package redux.test;

import org.junit.Test;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;
import redux.*;
import redux.test.utils.RunnableAction;
import redux.test.utils.SpyState;
import redux.test.utils.TrivialStore;
import redux.test.utils.SpyListener;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StoreTest {
    @Test
    public void accepts_initial_state() {

        Store store = new Store(TrivialStore.reducer, TrivialStore.state);

        assertEquals(TrivialStore.state, store.getState());
    }

    @Test
    public void should_dispatch_action() {
        TodoState initialState = new TodoState();
        Store store = new Store(TodoStore.reducer, initialState);

        store.dispatch(TodoStore.addTodo("0"));

        TodoState newList = (TodoState) store.getState();
        assertEquals(1, newList.size());
    }

    @Test
    public void preserve_state_when_replacing_reducer() {
        TodoState initialState = new TodoState("1");
        Store store = new Store(TodoStore.reducer, initialState);

        store.dispatch(TodoStore.addTodo("2"));
        assertEquals(2, ((TodoState) store.getState()).size());
        assertEquals("1", ((TodoState) store.getState()).getText(0));
        assertEquals("2", ((TodoState) store.getState()).getText(1));

        store.replaceReducer(TodoStore.reverseReducer);
        assertEquals(2, ((TodoState) store.getState()).size());
        assertEquals("1", ((TodoState) store.getState()).getText(0));
        assertEquals("2", ((TodoState) store.getState()).getText(1));

        store.dispatch(TodoStore.addTodo("0"));
        assertEquals(3, ((TodoState) store.getState()).size());
        assertEquals("0", ((TodoState) store.getState()).getText(0));
        assertEquals("1", ((TodoState) store.getState()).getText(1));
        assertEquals("2", ((TodoState) store.getState()).getText(2));

        store.replaceReducer(TodoStore.reducer);
        assertEquals(3, ((TodoState) store.getState()).size());
        assertEquals("0", ((TodoState) store.getState()).getText(0));
        assertEquals("1", ((TodoState) store.getState()).getText(1));
        assertEquals("2", ((TodoState) store.getState()).getText(2));

        store.dispatch(TodoStore.addTodo("3"));
        assertEquals(4, ((TodoState) store.getState()).size());
        assertEquals("0", ((TodoState) store.getState()).getText(0));
        assertEquals("1", ((TodoState) store.getState()).getText(1));
        assertEquals("2", ((TodoState) store.getState()).getText(2));
        assertEquals("3", ((TodoState) store.getState()).getText(3));
    }

    @Test
    public void supports_subscription() {
        Store store = new Store(TodoStore.reducer, new TodoState());

        SpyListener listener = new SpyListener();
        store.dispatch(TodoStore.addTodo("1"));
        assertEquals(0, listener.getCallCount());

        Subscription subscription = store.subscribe(listener);
        assertTrue(subscription.isSubscribed());

        store.dispatch(TodoStore.addTodo("2"));
        assertEquals(1, listener.getCallCount());

        store.dispatch(TodoStore.addTodo("3"));
        assertEquals(2, listener.getCallCount());
    }

    @Test
    public void supports_subscription_cancel() {
        Store store = new Store(TodoStore.reducer, new TodoState());
        SpyListener listener = new SpyListener();

        Subscription subscription = store.subscribe(listener);
        assertTrue(subscription.isSubscribed());

        store.dispatch(TodoStore.addTodo("1"));
        assertEquals(1, listener.getCallCount());

        subscription.cancel();
        assertFalse(subscription.isSubscribed());

        store.dispatch(TodoStore.addTodo("2"));
        assertEquals(1, listener.getCallCount());
    }

    @Test
    public void remove_relevant_listener_when_subscription_is_canceled() {
        Store store = new Store(TrivialStore.reducer, TrivialStore.state);
        SpyListener listener1 = new SpyListener();
        SpyListener listener2 = new SpyListener();
        Subscription subscription1 = store.subscribe(listener1);
        store.subscribe(listener2);

        store.dispatch(Action.empty);
        subscription1.cancel();
        store.dispatch(Action.empty);

        assertEquals(1, listener1.getCallCount());
        assertEquals(2, listener2.getCallCount());
    }

    @Test
    public void supports_removing_subscription_inside_another_subscription() {
        Store store = new Store(TrivialStore.reducer, TrivialStore.state);
        ArrayList<Subscription> toCancel = new ArrayList<>();
        Subscription subscription1 = store.subscribe(new SpyListener());
        Subscription subscription2 = store.subscribe(() ->
                toCancel.forEach(Subscription::cancel));
        toCancel.add(subscription1);
        toCancel.add(subscription2);

        store.dispatch(Action.empty);

        assertFalse(subscription1.isSubscribed());
        assertFalse(subscription2.isSubscribed());
    }

    @Test
    public void uses_the_last_snapshot_of_subscribers_during_nested_dispatch() {
        Store store = new Store(TrivialStore.reducer, TrivialStore.state);
        SpyListener listener1 = new SpyListener();
        SpyListener listener2 = new SpyListener();
        SpyListener listener3 = new SpyListener();
        SpyListener listener4 = new SpyListener();

        ArrayList<Subscription> subscription4 = new ArrayList<>();
        ArrayList<Subscription> toCancel = new ArrayList<>();
        Subscription withDispatch = store.subscribe(() -> {
            listener1.call();
            assertEquals(1, listener1.getCallCount());
            assertEquals(0, listener2.getCallCount());
            assertEquals(0, listener3.getCallCount());
            assertEquals(0, listener4.getCallCount());

            toCancel.forEach(Subscription::cancel);
            subscription4.add(store.subscribe(listener4));
            store.dispatch(Action.empty);

            assertEquals(1, listener1.getCallCount());
            assertEquals(1, listener2.getCallCount());
            assertEquals(1, listener3.getCallCount());
            assertEquals(1, listener4.getCallCount());
        });
        toCancel.add(withDispatch);
        store.subscribe(listener2);
        store.subscribe(listener3);

        store.dispatch(Action.empty);
        assertEquals(1, listener1.getCallCount());
        assertEquals(2, listener2.getCallCount());
        assertEquals(2, listener3.getCallCount());
        assertEquals(1, listener4.getCallCount());

        subscription4.forEach(Subscription::cancel);
        store.dispatch(Action.empty);
        assertEquals(1, listener1.getCallCount());
        assertEquals(3, listener2.getCallCount());
        assertEquals(3, listener3.getCallCount());
        assertEquals(1, listener4.getCallCount());
    }

    @Test
    public void provides_an_up_to_date_state_when_a_subscriber_is_notified() {
        Store store = new Store(TodoStore.reducer, new TodoState());
        ArrayList<String> result = new ArrayList<>();
        store.subscribe(() ->
                result.add(((TodoState) store.getState()).getText(0))
        );

        store.dispatch(TodoStore.addTodo("done"));

        assertEquals("done", result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    public void handles_nested_dispatches_gracefully() {
        Store store = new Store(SpyState.reducer, new SpyState());
        store.subscribe(() -> {
            SpyState state = (SpyState) store.getState();
            if (!state.getActionLog().contains("2")) {
                store.dispatch(SpyState.action("2"));
            }
        });

        store.dispatch(SpyState.action("1"));
        store.dispatch(SpyState.action("1"));

        SpyState newState = (SpyState) store.getState();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("1");
        assertEquals(expected, newState.getActionLog());
    }

    @Test
    public void does_not_allow_dispatch_from_within_a_reducer() {
        Reducer reducer = (state, action) -> {
            if (action instanceof RunnableAction) {
                ((RunnableAction) action).callBack.run();
            }
            return state;
        };
        Store store = new Store(reducer, new TrivialStore());
        Action action = new RunnableAction(() ->
                store.dispatch(Action.empty)
        );

        boolean gotError = false;
        try {
            store.dispatch(action);
        } catch (ReduxError.DispatchInReducerError error) {
            gotError = true;
        }

        assertTrue(gotError);
    }

    @Test
    public void recovers_from_an_error_within_a_reducer() {
        Reducer reducer = (state, action) -> {
            throw new Error();
        };
        Store store = new Store(reducer, new TrivialStore());

        store.dispatch(Action.empty);
    }
}

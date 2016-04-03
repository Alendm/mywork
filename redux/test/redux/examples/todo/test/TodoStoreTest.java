package redux.examples.todo.test;

import org.junit.Test;
import redux.Action;
import redux.State;
import redux.Store;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;
import redux.test.utils.TrivialStore;

import static org.junit.Assert.assertEquals;

public class TodoStoreTest {
    @Test
    public void reducer_should_accept_addTodo_action() {
        TodoState list = new TodoState();
        assertEquals(0, list.size());

        TodoState newList = (TodoState) TodoStore.reducer.reduce(list, TodoStore.addTodo("1"));

        assertEquals(1, newList.size());
    }

    @Test
    public void reducer_should_accept_toggleTodo_action() throws Exception {
        TodoState initialState = new TodoState("1");
        Store store = new TodoStore(initialState);

        store.dispatch(TodoStore.toggleTodo(initialState.getId(0)));
        TodoState state = (TodoState) store.getState();

        assertEquals(initialState.size(), state.size());
        assertEquals(initialState.getId(0), state.getId(0));
        assertEquals(initialState.getText(0), state.getText(0));
        assertEquals(!initialState.isCompleted(0), state.isCompleted(0));
    }

    @Test
    public void reducer_should_ignore_unknown_store() {
        State newState = TodoStore.reducer.reduce(TrivialStore.state, TodoStore.addTodo("1"));

        assertEquals(TrivialStore.state, newState);
    }

    @Test
    public void reducer_should_ignore_unknown_action() {
        TodoState state = new TodoState();

        State newState = TodoStore.reducer.reduce(state, Action.empty);

        assertEquals(state, newState);
    }

    @Test
    public void reducer_should_add_todo_to_the_end() {
        TodoState state = new TodoState("1");

        TodoState newState = (TodoState) TodoStore.reducer.reduce(state, TodoStore.addTodo("2"));

        assertEquals("1", newState.getText(0));
        assertEquals("2", newState.getText(1));
    }

    @Test
    public void reverseReducer_should_add_todo_to_the_beginning() {
        TodoState state = new TodoState("1");

        TodoState newState = (TodoState) TodoStore.reverseReducer.reduce(state, TodoStore.addTodo("2"));

        assertEquals("2", newState.getText(0));
        assertEquals("1", newState.getText(1));
    }
}

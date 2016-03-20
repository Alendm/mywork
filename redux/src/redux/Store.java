package redux;

import java.util.ArrayList;

public class Store {

    public State getState() {
        return currentState;
    }

    public Action dispatch(Action action) {
        if (isDispatching) {
            throw new ReduxError.DispatchInReducerError();
        }
        try {
            isDispatching = true;
            currentState = currentReducer.reduce(currentState, action);
        } finally {
            isDispatching = false;
        }

        currentListeners = nextListeners;
        ArrayList<Listener> listeners = currentListeners;
        listeners.forEach(Listener::call);
        return action;
    }

    public Subscription subscribe(Listener listener) {
        ensureCanMutateNextListeners();
        nextListeners.add(listener);
        return new Subscription(() -> {
            ensureCanMutateNextListeners();
            int index = nextListeners.indexOf(listener);
            nextListeners.remove(index);
        });
    }

    public void replaceReducer(Reducer nextReducer) throws ReduxError.DispatchInReducerError {
        currentReducer = nextReducer;
        dispatch(Action.empty);
    }

    private State currentState;
    private Reducer currentReducer;
    private ArrayList<Listener> currentListeners;
    private ArrayList<Listener> nextListeners;
    private boolean isDispatching = false;

    public Store(Reducer reducer, State initialState) {
        currentReducer = reducer;
        currentState = initialState;
        currentListeners = new ArrayList<>();
        nextListeners = currentListeners;
    }

    private void ensureCanMutateNextListeners() {
        if (nextListeners == currentListeners) {
            nextListeners = new ArrayList<>(currentListeners);
        }
    }
}


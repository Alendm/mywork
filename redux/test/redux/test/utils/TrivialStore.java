package redux.test.utils;

import redux.Reducer;
import redux.State;
import redux.Store;

public class TrivialStore extends Store implements State {
    public static final Reducer reducer = (s, a) -> s;
    public static final State state = new TrivialStore();

    public TrivialStore() {
        super(reducer, state);
    }
}

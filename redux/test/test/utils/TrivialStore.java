package test.utils;

import redux.Reducer;
import redux.State;

public class TrivialStore implements State {
    public static final Reducer reducer = (s, a) -> s;
    public static final State state = new TrivialStore();
}

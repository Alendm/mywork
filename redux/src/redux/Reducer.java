package redux;

public interface Reducer {
    State reduce(State state, Action action);
}

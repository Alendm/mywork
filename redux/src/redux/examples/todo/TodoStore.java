package redux.examples.todo;

import redux.Action;
import redux.Reducer;
import redux.Store;

import java.util.UUID;

public class TodoStore extends Store {
    private static class AddTodoAction implements Action {
        private final String text;

        private AddTodoAction(String text) {
            this.text = text;
        }
    }

    private static class ToggleTodoAction implements Action {
        private final UUID id;

        private ToggleTodoAction(UUID id) {
            this.id = id;
        }
    }

    private static Reducer generateReducer(Reducer reducer) {
        return (state, action) -> {
            if (!(state instanceof TodoState)) {
                return state;
            }
            TodoState todoState = (TodoState) state;

            if (action instanceof AddTodoAction) {
                return reducer.reduce(state, action);
            }

            if (action instanceof ToggleTodoAction) {
                return todoState.toggleTodo(((ToggleTodoAction) action).id);
            }

            return state;
        };
    }

    public static final Reducer reducer = generateReducer(
            (s, a) -> ((TodoState) s).appendTodo(((AddTodoAction) a).text)
    );

    public static final Reducer reverseReducer = generateReducer(
            (s, a) -> ((TodoState) s).insertTodo( ((AddTodoAction) a).text, 0)
    );

    public TodoStore(TodoState todoState) {
        super(reducer, todoState);
    }

    public static Action addTodo(String text) {
        return new AddTodoAction(text);
    }

    public static Action toggleTodo(UUID uuid) {
        return new ToggleTodoAction(uuid);
    }

}

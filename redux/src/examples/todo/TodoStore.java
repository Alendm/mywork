package examples.todo;

import redux.Action;
import redux.Reducer;

public class TodoStore {
    public static final Reducer reducer = (state, action) -> {
        if (!(state instanceof TodoList)) {
            return state;
        }
        TodoList todoList = (TodoList) state;

        if (action instanceof AddTodoAction) {
            AddTodoAction todoAction = (AddTodoAction) action;
            return todoList.appendTodo(todoAction.text);
        }

        return state;
    };
    public static final Reducer reverseReducer = (state, action) -> {
        if (!(state instanceof TodoList)) {
            return state;
        }
        TodoList todoList = (TodoList) state;

        if (action instanceof AddTodoAction) {
            AddTodoAction todoAction = (AddTodoAction) action;
            return todoList.insertTodo(todoAction.text, 0);
        }

        return state;
    };

    public static Action addTodo(String text) {
        return new AddTodoAction(text);
    }
}

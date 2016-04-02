package redux.examples.todo;

import redux.Action;

public class AddTodoAction implements Action {
    public final String text;

    public AddTodoAction(String text) {
        this.text = text;
    }
}

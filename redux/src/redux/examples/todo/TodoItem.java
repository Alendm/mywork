package redux.examples.todo;

import java.util.UUID;

public class TodoItem {
    public final UUID id;
    public final String text;
    public final boolean completed;

    TodoItem(String text) {
        this(UUID.randomUUID(), text, false);
    }

    private TodoItem(UUID id, String text, boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    TodoItem toggled() {
        return new TodoItem(id, text, !completed);
    }
}

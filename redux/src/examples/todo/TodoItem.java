package examples.todo;

import java.util.UUID;

public class TodoItem {
    private final UUID id;
    public final String text;

    public TodoItem(String text) {
        this.id = UUID.randomUUID();
        this.text = text;
    }
}

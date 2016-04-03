package redux.examples.todo;

import redux.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TodoState implements State {
    private List<TodoItem> itemList;

    public TodoState() {
        this.itemList = new ArrayList<>();
    }

    public TodoState(String... items) {
        List<TodoItem> itemList = Arrays.stream(items)
                .map(TodoItem::new)
                .collect(Collectors.toList());
        this.itemList = new ArrayList<>(itemList);
    }

    private TodoState(List<TodoItem> itemList) {
        this.itemList = itemList;
    }

    public TodoState appendTodo(String text) {
        ArrayList<TodoItem> newList = new ArrayList<>(itemList);
        newList.add(new TodoItem(text));
        return new TodoState(newList);
    }

    public TodoState insertTodo(String text, int index) {
        ArrayList<TodoItem> newList = new ArrayList<>(itemList);
        newList.add(index, new TodoItem(text));
        return new TodoState(newList);
    }

    public TodoState toggleTodo(UUID id) {
        List<TodoItem> newList = itemList.stream()
                .map(item -> item.id == id ? item.toggled() : item)
                .collect(Collectors.toList());
        return new TodoState(newList);
    }

    public UUID getId(int index) {
        return itemList.get(index).id;
    }

    public int size() {
        return itemList.size();
    }

    public String getText(int index) {
        return itemList.get(index).text;
    }

    public boolean isCompleted(int index) {
        return itemList.get(index).completed;
    }

    public Stream<TodoItem> stream() {
        return itemList.stream();
    }
}

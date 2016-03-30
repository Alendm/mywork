package redux.examples.todo;

import redux.State;

import java.util.ArrayList;

public class TodoList implements State {
    public ArrayList<TodoItem> itemList;

    public TodoList() {
        this.itemList = new ArrayList<>();
    }

    private TodoList(ArrayList<TodoItem> itemList) {

        this.itemList = itemList;
    }

    public TodoList appendTodo(String text) {
        ArrayList<TodoItem> newList = new ArrayList<>(itemList);
        newList.add(new TodoItem(text));
        return new TodoList(newList);
    }

    public TodoList insertTodo(String text, int index) {
        ArrayList<TodoItem> newList = new ArrayList<>(itemList);
        newList.add(index, new TodoItem(text));
        return new TodoList(newList);
    }
}

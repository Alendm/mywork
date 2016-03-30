package redux.examples.todo.test;

import org.junit.Test;
import redux.examples.todo.TodoList;
import redux.examples.todo.TodoStore;
import redux.Action;
import redux.State;
import redux.test.utils.TrivialStore;

import static org.junit.Assert.*;

public class TodoReducer {
    @Test
    public void should_accept_addTodo_action() throws Exception {
        TodoList list = new TodoList();
        assertEquals(0, list.itemList.size());

        TodoList newList = (TodoList) TodoStore.reducer.reduce(list, TodoStore.addTodo("redux/test"));

        assertEquals(1, newList.itemList.size());
    }

    @Test
    public void should_ignore_unknown_store() throws Exception {
        State newState = TodoStore.reducer.reduce(TrivialStore.state, TodoStore.addTodo("redux/test"));

        assertEquals(TrivialStore.state, newState);
    }

    @Test
    public void should_ignore_unknown_action() throws Exception {
        TodoList list = new TodoList();

        State newState = TodoStore.reducer.reduce(list, Action.empty);

        assertEquals(list, newState);
    }

    @Test
    public void reducer_should_add_todo_to_the_end() throws Exception {
        TodoList list = (new TodoList()).appendTodo("1");

        TodoList newList = (TodoList) TodoStore.reducer.reduce(list, TodoStore.addTodo("2"));

        assertEquals("1", newList.itemList.get(0).text);
        assertEquals("2", newList.itemList.get(1).text);
    }

    @Test
    public void reverseReducer_should_add_todo_to_the_beginning() throws Exception {
        TodoList list = (new TodoList()).appendTodo("1");

        TodoList newList = (TodoList) TodoStore.reverseReducer.reduce(list, TodoStore.addTodo("2"));

        assertEquals("2", newList.itemList.get(0).text);
        assertEquals("1", newList.itemList.get(1).text);
    }
}

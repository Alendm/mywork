package redux.examples.todo.test;

import org.junit.Test;
import static org.junit.Assert.*;

import redux.examples.todo.TodoList;


public class TodoState {
    @Test
    public void should_be_empty_upon_initialization() throws Exception {
        TodoList list = new TodoList();

        assertEquals(0, list.itemList.size());
    }

    @Test
    public void should_not_modify_itself_on_item_addition() throws Exception {
        TodoList list = new TodoList();

        TodoList newList = list.appendTodo("redux/test");

        assertEquals(0, list.itemList.size());
        assertEquals(1, newList.itemList.size());
    }

    @Test
    public void appendTodo_should_add_item_to_the_end() throws Exception {
        TodoList list = (new TodoList()).appendTodo("0");

        TodoList newList = list.appendTodo("1");

        assertEquals("0", newList.itemList.get(0).text);
        assertEquals("1", newList.itemList.get(1).text);
    }

    @Test
    public void insertTodo_should_add_item_to_the_specified_index() throws Exception {
        TodoList list = (new TodoList()).appendTodo("0");

        TodoList newList = list.insertTodo("1", 0);

        assertEquals("1", newList.itemList.get(0).text);
        assertEquals("0", newList.itemList.get(1).text);
    }
}

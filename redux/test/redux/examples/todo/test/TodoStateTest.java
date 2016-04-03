package redux.examples.todo.test;

import org.junit.Test;

import static org.junit.Assert.*;

import redux.examples.todo.TodoState;

import java.util.UUID;


public class TodoStateTest {
    @Test
    public void should_be_empty_upon_initialization() {
        TodoState list = new TodoState();

        assertEquals(0, list.size());
    }

    @Test
    public void should_create_new_item_as_not_completed() {
        TodoState state = new TodoState("1");

        assertEquals(1, state.size());
        assertFalse(state.isCompleted(0));
    }

    @Test
    public void should_be_able_to_toggle_state() {
        TodoState state = new TodoState("1");
        UUID id = state.getId(0);

        TodoState newState = state.toggleTodo(id);

        assertEquals(1, newState.size());
        assertEquals("1", newState.getText(0));
        assertEquals(id, newState.getId(0));
        assertTrue(newState.isCompleted(0));
    }

    @Test
    public void should_not_modify_itself_on_item_state_toggle() {
        TodoState state = new TodoState("1");
        UUID id = state.getId(0);

        state.toggleTodo(id);

        assertEquals(1, state.size());
        assertEquals("1", state.getText(0));
        assertEquals(id, state.getId(0));
        assertFalse(state.isCompleted(0));
    }

    @Test
    public void should_not_modify_itself_on_item_addition() {
        TodoState state = new TodoState("1");
        UUID id = state.getId(0);
        boolean completed = state.isCompleted(0);

        TodoState newList = state.appendTodo("2");
        assertEquals(2, newList.size());

        assertEquals(1, state.size());
        assertEquals("1", state.getText(0));
        assertEquals(id, state.getId(0));
        assertEquals(completed, state.isCompleted(0));
    }

    @Test
    public void appendTodo_should_add_item_to_the_end() {
        TodoState list = new TodoState("0");

        TodoState newList = list.appendTodo("1");

        assertEquals(2, newList.size());
        assertEquals("0", newList.getText(0));
        assertEquals("1", newList.getText(1));
    }

    @Test
    public void insertTodo_should_add_item_to_the_specified_index() {
        TodoState list = new TodoState("0","2");

        TodoState newList = list.insertTodo("1", 1);

        assertEquals(3, newList.size());
        assertEquals("0", newList.getText(0));
        assertEquals("1", newList.getText(1));
        assertEquals("2", newList.getText(2));
    }
}

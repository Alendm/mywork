package redux.examples.todo.cli.test;

import org.junit.Test;
import redux.Action;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;
import redux.examples.todo.cli.ReduxCliTodoApplication;

import java.util.UUID;

import static org.junit.Assert.*;


public class ProcessTest {
    @Test
    public void заканчиваем_работу_если_ввод_пустой() {
        String result = ReduxCliTodoApplication.process("");

        assertNull(result);
    }

    @Test
    public void заканчиваем_работу_если_введено_выход() {
        String result = ReduxCliTodoApplication.process("выход");

        assertNull(result);
    }

//    @Test
//    public void умеет_добавлять_задачу() {
//        String result = ReduxCliTodoApplication.process("Д Задача");
//        String expected = "1. Задача\n> ";
//        assertEquals(expected, result);
//    }

    @Test
    public void печать_состояния() {
        TodoStore store = new TodoStore(new TodoState("Задача1", "Задача2"));
        String actual = ReduxCliTodoApplication.printStore(store);

        String expected = "1. Задача1\n2. Задача2\n> ";
        assertEquals(expected, actual);
    }

    @Test
    public void печать_пустого_состояния() {
        TodoStore store = new TodoStore();
        String actual = ReduxCliTodoApplication.printStore(store);

        String expected = "> ";
        assertEquals(expected, actual);
    }

    @Test
    public void печать_завершенного_состояния() {
        TodoStore store = new TodoStore(new TodoState("Задача1", "Задача2"));
        TodoState state = (TodoState) store.getState();
        UUID uuid = state.getId(0);
        store.dispatch(TodoStore.toggleTodo(uuid));

        String actual = ReduxCliTodoApplication.printStore(store);

        String expected = "1. Задача1 (завершено)\n2. Задача2\n> ";
        assertEquals(expected, actual);
    }

    @Test
    public void разбор_строки_добавления_задачи() {
        Action action = ReduxCliTodoApplication.parse("д Задача");

        Action expected = TodoStore.addTodo("Задача");
        assertEquals(expected, action);
    }

    @Test
    public void разбор_неправильной_строки() {
        Action action = ReduxCliTodoApplication.parse("строка");

        Action expected = Action.empty;
        assertEquals(expected, action);
    }

    @Test
    public void разбор_строки_переключения_задачи_с_большим_количеством_пробелов() {
        ReduxCliTodoApplication.store = new TodoStore(new TodoState("Задача1", "Задача2"));
        TodoState state = (TodoState) ReduxCliTodoApplication.store.getState();
        UUID uuid = state.getId(0);

        Action action = ReduxCliTodoApplication.parse("п        1");

        Action expected = TodoStore.toggleTodo(uuid);
        assertEquals(expected, action);
    }

    @Test
    public void разбор_строки_переключения_задачи() {
        ReduxCliTodoApplication.store = new TodoStore(new TodoState("Задача1", "Задача2"));
        TodoState state = (TodoState) ReduxCliTodoApplication.store.getState();
        UUID uuid = state.getId(0);

        Action action = ReduxCliTodoApplication.parse("п 1");

        Action expected = TodoStore.toggleTodo(uuid);
        assertEquals(expected, action);
    }

    @Test
    public void разбор_строки_переключения_задачи_нечисловой_ввод() {
        ReduxCliTodoApplication.store = new TodoStore(new TodoState("Задача1", "Задача2"));

        Action action = ReduxCliTodoApplication.parse("п п");

        Action expected = Action.empty;
        assertEquals(expected, action);
    }

    @Test
    public void разбор_строки_переключения_задачи_индекс_больше_размера_списка() {
        ReduxCliTodoApplication.store = new TodoStore(new TodoState("Задача1", "Задача2"));

        Action action = ReduxCliTodoApplication.parse("п 3");

        Action expected = Action.empty;
        assertEquals(expected, action);
    }

    @Test
    public void разбор_строки_переключения_задачи_ненатуральный_индекс() {
        ReduxCliTodoApplication.store = new TodoStore(new TodoState("Задача1", "Задача2"));

        Action action = ReduxCliTodoApplication.parse("п 0");

        Action expected = Action.empty;
        assertEquals(expected, action);
    }
}

package redux.examples.todo.fx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import fxtestrunner.JavaFxJUnit4ClassRunner;
import redux.Store;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;
import redux.examples.todo.fx.MainSection;
import redux.test.utils.TrivialStore;

import static org.junit.Assert.*;

@RunWith(JavaFxJUnit4ClassRunner.class)
public class MainSectionTest {
    @Test
    public void has_no_children_from_unknown_store() {
        Store store = new TrivialStore();

        MainSection mainSection = new MainSection(store);

        assertEquals(0, mainSection.getChildren().size());
    }

    @Test
    public void children_count_is_equal_to_todo_items_count() {
        TodoState todoState = new TodoState("0", "1");
        Store store = new TodoStore(todoState);

        MainSection mainSection = new MainSection(store);

        assertEquals(todoState.size(), mainSection.getChildren().size());
    }

    @Test
    public void should_increase_children_count_on_addTodo_action() throws Exception {
        Store store = new TodoStore(new TodoState());

        MainSection mainSection = new MainSection(store);
        assertEquals(0, mainSection.getChildren().size());

        store.dispatch(TodoStore.addTodo("test"));
        assertEquals(1, mainSection.getChildren().size());
    }
}


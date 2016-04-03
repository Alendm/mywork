package redux.examples.todo.fx;

import javafx.scene.layout.VBox;
import redux.State;
import redux.Store;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;

import java.util.List;
import java.util.stream.Collectors;

public class MainSection extends VBox {
    private final Store store;
    private State state = null;

    private void update() {
        if (state == store.getState()) {
            return;
        }
        state = store.getState();
        if (!(state instanceof TodoState)) {
            return;
        }
        TodoState newState = (TodoState) state;

        List<TodoRow> items = newState.stream()
                .map(item -> new TodoRow(item, event -> store.dispatch(TodoStore.toggleTodo(item.id))))
                .collect(Collectors.toList());
        getChildren().setAll(items);
    }

    public MainSection(Store store) {
        super();
        this.store = store;
        update();
        store.subscribe(this::update);
    }

}

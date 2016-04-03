package redux.examples.todo.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import redux.examples.todo.TodoItem;

class TodoRow extends CheckBox {
    TodoRow(TodoItem item, EventHandler<ActionEvent> actionEventEventHandler) {
        super(item.text);
        setSelected(item.completed);
        setOnAction(actionEventEventHandler);
    }

}

package redux.examples.todo.fx;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

class InputRow extends HBox {

    private final TextField textField = new TextField();
    private final InputHandler onInput;

    private void onAction(ActionEvent event) {
        onInput.handle(textField.getText());
        textField.setText("");
    }

    InputRow(InputHandler onInput) {
        super();

        this.onInput = onInput;

        setHgrow(textField, Priority.ALWAYS);
        textField.setOnAction(this::onAction);

        Button button = new Button("Add Todo");
        button.setOnAction(this::onAction);

        getChildren().setAll(
                textField,
                button
        );
    }
}

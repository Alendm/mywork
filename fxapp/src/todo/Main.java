package todo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final VBox items = new VBox();
    private final TextField itemName = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FX Todo");

        BorderPane root = new BorderPane();
        root.setTop(createInputControl());
        root.setCenter(items);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private Node createInputControl() {
        HBox hBox = new HBox();

        HBox.setHgrow(itemName, Priority.ALWAYS);
        itemName.setOnAction(this::addTodo);

        Button addItem = new Button("Add Todo");
        addItem.setOnAction(this::addTodo);

        hBox.getChildren().addAll(itemName, addItem);
        return hBox;
    }

    private void addTodo(@SuppressWarnings("UnusedParameters") ActionEvent event) {
        items.getChildren().add(new CheckBox(itemName.getText()));
        itemName.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package todo;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

@SuppressWarnings("WeakerAccess")
public class FxTodoApplication extends Application {

    private final VBox items = new VBox();
    private final TextField itemName = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FX Todo");

        BorderPane root = new BorderPane();
        final Node myInputControl = createInputControl();
        root.setTop(myInputControl);
        root.setCenter(items);

        final Scene myScene = new Scene(root, 300, 275);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private Node createInputControl() {
        HBox.setHgrow(itemName, Priority.ALWAYS);
        itemName.setOnAction(this::addTodo);

        Button addItem = new Button("Add Todo");
        addItem.setOnAction(this::addTodo);

        HBox hBox = new HBox();
        ObservableList<Node> myList = hBox.getChildren();
        myList.addAll(itemName, addItem);
        return hBox;
    }

    private void addTodo(@SuppressWarnings("UnusedParameters") ActionEvent event) {
        final String myText = itemName.getText();
        final CheckBox myCheckBox = new CheckBox(myText);
        final ObservableList<Node> myList = items.getChildren();
        myList.add(myCheckBox);
        cleanInputField();
    }

    private void cleanInputField() {
        itemName.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package todo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private VBox items;

    @Override
    public void init() throws Exception {
        super.init();
        items = new VBox();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Simple Todo");

        BorderPane root = new BorderPane();
        root.setTop(createInputControl());
        root.setCenter(items);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private Node createInputControl() {
        HBox hBox = new HBox();

        TextField itemName = new TextField();
        HBox.setHgrow(itemName, Priority.ALWAYS);
        itemName.setOnAction((ActionEvent event) -> {
            items.getChildren().add(createItem(itemName.getText()));
            itemName.setText("");
        });

        Button addItem = new Button("Add Todo");
        addItem.setOnAction((ActionEvent event) -> {
            items.getChildren().add(createItem(itemName.getText()));
            itemName.setText("");
        });

        hBox.getChildren().addAll(itemName, addItem);
        return hBox;
    }

    private Node createItem(String name) {
        HBox hBox = new HBox();
        hBox.getChildren().add(new CheckBox());
        hBox.getChildren().add(new Label(name));
        return hBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

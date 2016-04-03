package redux.examples.todo.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;

@SuppressWarnings("WeakerAccess")
public class ReduxFxTodoApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Redux Todo");
        BorderPane root = new BorderPane();

        TodoStore store = new TodoStore(new TodoState());

        root.setTop(new InputRow(text -> store.dispatch(TodoStore.addTodo(text))));

        root.setCenter(new MainSection(store));


        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

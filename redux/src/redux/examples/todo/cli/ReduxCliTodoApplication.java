package redux.examples.todo.cli;

import redux.Action;
import redux.examples.todo.TodoState;
import redux.examples.todo.TodoStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

public class ReduxCliTodoApplication {
    public static TodoStore store = new TodoStore();

    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String output = "> ";
        do {
            System.out.print(output);
            output = process(getInput(console));
        }
        while (output != null);
    }

    public static Action parse(String input) {
        if (input.startsWith("д ")) {
            String text = input.substring(2);
            return  TodoStore.addTodo(text);
        }
        else if (input.startsWith("п ")) {
            String text = input.substring(2);
            int number;
            try {
                number = Integer.parseInt(text.trim());
            } catch (NumberFormatException ignore) {
                return Action.empty;
            }
            TodoState state = (TodoState) store.getState();
            final int index = number - 1;
            if (index >= state.size() || index < 0) {
                return Action.empty;
            }
            UUID uuid = state.getId(index);
            return  TodoStore.toggleTodo(uuid);
        }
        return  Action.empty;
    }

    public static String printStore(TodoStore store) {
        TodoState state = (TodoState) store.getState();
        String result = "";
        for (int i = 0; i < state.size(); i++) {
            String text = state.getText(i);
            String index = (new Integer(i + 1)).toString();
            boolean isCompleted = state.isCompleted(i);
            String completed = "";
            if (isCompleted) {
                completed = " (завершено)";
            }
            String line = index + ". " + text + completed + "\n";
            result += line;
        }
        result += "> ";
        return result;
    }

    public static String process(String input) {
        if (input == null || input.equals("") || input.equals("выход")) {
            return null;
        }
        Action action = parse(input);
        store.dispatch(action);
        return printStore(store);
    }

    private static String getInput(BufferedReader console) {
        try {
            return console.readLine();
        } catch (Throwable error) {
            System.out.println("error:" + error);
        }
        return null;
    }

}


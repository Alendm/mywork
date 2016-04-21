package redux.examples.todo.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReduxCliTodoApplication {
    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String output = "> ";
        do {
            System.out.print(output);
            output = process(getInput(console));
        }
        while (output != null);
    }

    private static String process(String input) {
        if (input == null || input.equals("exit")) {
            return null;
        }
        return "got: " + input + "\n> ";
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


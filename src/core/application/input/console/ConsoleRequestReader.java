package core.application.input.console;

import core.application.input.RequestReader;

import java.util.Scanner;

public class ConsoleRequestReader implements RequestReader<String> {

    private static final String NEW_COMMAND_START = ">";

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(NEW_COMMAND_START);
            String input = scanner.nextLine();
            if(!input.isBlank()) {
                return input;
            }
        }
    }
}

package core.application.input.console;

import core.application.input.RequestReader;
import core.application.input.constant.InputConstant;

import java.util.Scanner;

public class ConsoleRequestReader implements RequestReader<String> {



    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(InputConstant.NEW_COMMAND_START.getValue());
            String input = scanner.nextLine();
            if(!input.isBlank()) {
                return input;
            }
        }
    }
}

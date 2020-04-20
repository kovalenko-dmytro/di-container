package core.application.input.console;

import core.application.input.RequestParser;
import core.application.input.console.entity.ConsoleRequest;

public class ConsoleRequestParser implements RequestParser<ConsoleRequest> {

    @Override
    public ConsoleRequest parse(String input) {
        System.out.println("parsing input...");
        return new ConsoleRequest();
    }
}

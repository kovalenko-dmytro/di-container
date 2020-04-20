package core.application.factory.runner;

import core.application.factory.Runner;
import core.application.input.RequestParser;
import core.application.input.RequestReader;
import core.application.input.console.ConsoleRequestParser;
import core.application.input.console.ConsoleRequestReader;
import core.application.input.console.entity.ConsoleRequest;
import core.application.resolve.Resolver;
import core.application.resolve.console.ConsoleControllerResolver;
import core.ioc.exception.ApplicationException;

public class ConsoleRunner implements Runner {

    private static final String EXIT_COMMAND_NAME = "exit";

    private RequestReader<String> reader;
    private RequestParser<ConsoleRequest> parser;
    private Resolver<ConsoleRequest> resolver;

    public ConsoleRunner() {
        reader = new ConsoleRequestReader();
        parser = new ConsoleRequestParser();
        resolver = new ConsoleControllerResolver();
    }

    @Override
    public void run(String ... args) {
        String input = null;
        while (!EXIT_COMMAND_NAME.equalsIgnoreCase(input)) {
            input = reader.read();
            try {
                ConsoleRequest request = parser.parse(input);
                resolver.resolve(request);
            } catch (ApplicationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

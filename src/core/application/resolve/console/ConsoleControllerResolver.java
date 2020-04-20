package core.application.resolve.console;

import core.application.input.console.entity.ConsoleRequest;
import core.application.resolve.Resolver;

public class ConsoleControllerResolver implements Resolver<ConsoleRequest> {

    @Override
    public void resolve(ConsoleRequest param) {
        System.out.println("resolve console controller");
    }
}

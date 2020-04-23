package core.application.runner.factory;

import core.application.exception.ApplicationException;
import core.application.runner.constant.RunnerType;
import core.application.runner.factory.console.ConsoleCommandProvider;
import core.ioc.constant.ErrorMessage;

import java.text.MessageFormat;

public class CommandProviderFactory {

    public static CommandProvider getProvider(String name) throws ApplicationException {
        RunnerType type = RunnerType.getType(name);
        switch (type) {
            case CONSOLE:
                return new ConsoleCommandProvider();

            default:
                throw new ApplicationException(MessageFormat.format(ErrorMessage.RUNNER_TYPE_NOT_DEFINED.getValue(), type));
        }
    }
}

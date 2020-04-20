package core.application.runner;

import core.application.exception.ApplicationException;
import core.application.runner.console.ConsoleRunner;
import core.ioc.constant.ErrorMessage;
import core.ioc.constant.LaunchType;

public class RunnerFactory {

    public static Runner getRunner(LaunchType type) throws ApplicationException {
        switch (type) {
            case CONSOLE:
                return new ConsoleRunner();

            default:
                throw new ApplicationException(ErrorMessage.CANNOT_RESOLVE_LAUNCH_TYPE.getValue());
        }
    }
}

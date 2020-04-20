package core.application.factory;

import core.application.exception.ApplicationException;
import core.application.factory.runner.ConsoleRunner;
import core.ioc.constant.ErrorMessage;
import core.ioc.constant.LaunchType;

public class ApplicationFactory {

    public static Runner getRunner(LaunchType type) throws ApplicationException {
        switch (type) {
            case CONSOLE:
                return new ConsoleRunner();

            default:
                throw new ApplicationException(ErrorMessage.CANNOT_RESOLVE_LAUNCH_TYPE.getValue());
        }
    }
}

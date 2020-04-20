package core.application.runner;

import core.application.exception.ApplicationException;

public interface Runner {
    void run(String ... args) throws ApplicationException;
}

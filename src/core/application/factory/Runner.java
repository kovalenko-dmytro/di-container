package core.application.factory;

import core.application.exception.ApplicationException;

public interface Runner {
    void run(String ... args) throws ApplicationException;
}

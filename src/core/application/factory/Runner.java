package core.application.factory;

import core.ioc.exception.ApplicationException;

public interface Runner {
    void run(String ... args) throws ApplicationException;
}

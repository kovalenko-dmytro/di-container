package core.application.resolve;

import core.application.exception.ApplicationException;
import core.ioc.exception.BeanCreationException;

public interface Resolver<P> {
    void resolve(P request) throws ApplicationException, BeanCreationException;
}

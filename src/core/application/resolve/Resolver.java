package core.application.resolve;

import core.application.exception.ApplicationException;
import core.ioc.exception.BeanCreationException;

public interface Resolver<P, R> {
    R resolve(P request) throws ApplicationException, BeanCreationException;
}

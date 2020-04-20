package core.application.resolve;

import core.ioc.exception.ApplicationException;

public interface Resolver<P> {
    void resolve(P request) throws ApplicationException;
}

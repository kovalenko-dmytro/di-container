package core.application.resolve;

import core.application.exception.ApplicationException;

public interface Resolver<P> {
    void resolve(P request) throws ApplicationException;
}

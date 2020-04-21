package core.application.invoke;

import core.application.exception.ApplicationException;

public interface Invoker<P1, P2> {
    void invoke(P1 param1, P2 param2) throws ApplicationException;
}

package core.application.validate;

import core.application.exception.ApplicationException;

public interface Validator<P1, P2> {
    void validate(P1 param1, P2 param2) throws ApplicationException;
}

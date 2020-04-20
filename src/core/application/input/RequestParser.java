package core.application.input;

import core.application.exception.ApplicationException;

public interface RequestParser<T> {
    T parse(String input) throws ApplicationException;
}

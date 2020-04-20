package core.application.input;

import core.ioc.exception.ApplicationException;

public interface RequestParser<T> {
    T parse(String input) throws ApplicationException;
}

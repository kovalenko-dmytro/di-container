package core.application.configuration;

import core.application.exception.ApplicationException;

public interface ArgumentsParser {
    void parse(String ... args) throws ApplicationException;
}

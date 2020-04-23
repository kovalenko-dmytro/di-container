package core.application.configuration;

import core.application.exception.ApplicationException;

import java.util.Map;

public interface ArgumentsParser {
    Map<String, String> parse(String ... args) throws ApplicationException;
}

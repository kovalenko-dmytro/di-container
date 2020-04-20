package core.application.input.console;

import core.application.input.RequestParser;
import core.application.input.console.entity.ConsoleRequest;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.ApplicationException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleRequestParser implements RequestParser<ConsoleRequest> {

    private static final String REQUEST_PATH_REGEX = "^([^-]+)";
    private static final String REQUEST_PARAMS_REGEX = "(-\\S+)\\s*((\"(.*?)\")|([^-]\\S*))?";

    @Override
    public ConsoleRequest parse(String input) throws ApplicationException {
        ConsoleRequest request = new ConsoleRequest();
        request.setRequestPath(getRequestPath(input));
        request.setRequestParameters(getRequestParameters(input));
        return request;
    }

    private String getRequestPath(String input) throws ApplicationException {
        Matcher matcher = Pattern.compile(REQUEST_PATH_REGEX, Pattern.CASE_INSENSITIVE).matcher(input);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        throw new ApplicationException(ErrorMessage.CANNOT_PARSE_REQUEST_PATH.getValue());
    }

    private Map<String, String> getRequestParameters(String input) {
        Map<String, String> result = new LinkedHashMap<>();
        Matcher matcher = Pattern.compile(REQUEST_PARAMS_REGEX, Pattern.CASE_INSENSITIVE).matcher(input);
        while (matcher.find()) {
            String paramName = matcher.group(1);
            String paramValue = matcher.group(4);
            if (Objects.isNull(paramValue)) {
                paramValue = matcher.group(5);
            }
            result.put(paramName, paramValue);
        }
        return result;
    }
}

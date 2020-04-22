package core.application.invoke.console;

import core.application.exception.ApplicationException;
import core.application.input.entity.ConsoleRequest;
import core.application.invoke.Invoker;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.annotation.RequestMapping;
import core.application.resolve.entity.RequestPathMatchResult;
import core.ioc.constant.ErrorMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleControllerMethodInvoker implements Invoker<RequestPathMatchResult, ConsoleRequest> {

    @Override
    public void invoke(RequestPathMatchResult matchResult, ConsoleRequest consoleRequest) throws ApplicationException {
        Method pathMatchMethod = matchResult.getRequestPathMethod();
        try {
            pathMatchMethod.invoke(matchResult.getController(), mapRequestParameters(pathMatchMethod, consoleRequest));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ApplicationException(ErrorMessage.CANNOT_INVOKE_CONTROLLER_METHOD.getValue() + matchResult.getRequestPathMethod().getName());
        }
    }

    private Object[] mapRequestParameters(Method requestPathMethod, ConsoleRequest request) throws ApplicationException {
        List<Object> result = new ArrayList<>();
        for (Parameter parameter: requestPathMethod.getParameters()) {
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                String name = parameter.getAnnotation(PathVariable.class).name();
                String value = request.getRequestParameters().get(name);
                result.add(
                    Optional
                        .ofNullable(value)
                        .orElseThrow(() -> new ApplicationException(MessageFormat.format(
                            ErrorMessage.CANNOT_RESOLVE_PATH_VARIABLE.getValue(),
                            name,
                            requestPathMethod.getAnnotation(RequestMapping.class).path()))));
            }
        }
        return result.toArray();
    }
}

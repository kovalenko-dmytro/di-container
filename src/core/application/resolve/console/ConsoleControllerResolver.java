package core.application.resolve.console;

import core.application.exception.ApplicationException;
import core.application.input.entity.ConsoleRequest;
import core.application.resolve.Resolver;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.annotation.RequestMapping;
import core.application.resolve.constant.ResolveConstant;
import core.application.resolve.entity.RequestPathMatchResult;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConsoleControllerResolver implements Resolver<ConsoleRequest, RequestPathMatchResult> {

    @Override
    public RequestPathMatchResult resolve(ConsoleRequest request) throws ApplicationException, BeanCreationException {
        List<Object> controllers = BeanFactory.getInstance().getControllers();
        return findRequestPathMatch(controllers, request);
    }

    private RequestPathMatchResult findRequestPathMatch(List<Object> controllers, ConsoleRequest request) throws ApplicationException {
        String requestPath = resolveRequestPath(request);
        for (Object controller: controllers) {
            for (Method method: controller.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)
                    && method.getAnnotation(RequestMapping.class).path().equalsIgnoreCase(requestPath)) {
                    checkPathVariables(method, request);
                    RequestPathMatchResult result = new RequestPathMatchResult();
                    result.setController(controller);
                    result.setRequestPathMethod(method);
                    result.setParameterTypes(method.getParameterTypes());
                    return result;
                }
            }
        }
        throw new ApplicationException(ErrorMessage.CANNOT_RESOLVE_REQUEST_PATH.getValue());
    }

    private void checkPathVariables(Method method, ConsoleRequest request) throws ApplicationException {
        for (String requestParam: request.getRequestParameters().keySet()) {
                Arrays.stream(method.getParameters())
                    .filter(filterByPathVariableAnnotation(requestParam))
                    .findFirst()
                    .orElseThrow(() -> new ApplicationException(MessageFormat.format(
                        ErrorMessage.CANNOT_RESOLVE_PATH_VARIABLE.getValue(),
                        requestParam,
                        method.getAnnotation(RequestMapping.class).path())));
        }
    }

    private Predicate<Parameter> filterByPathVariableAnnotation(String requestParam) {
        return parameter ->
            parameter.isAnnotationPresent(PathVariable.class) &&
                requestParam.equalsIgnoreCase(parameter.getAnnotation(PathVariable.class).name());
    }

    private String resolveRequestPath(ConsoleRequest request) {
        return request.getRequestPath()
            .concat(ResolveConstant.SPACE.getValue())
            .concat(
                request.getRequestParameters().keySet()
                    .stream()
                    .map(pathVariable ->
                        ResolveConstant.OPEN_CURL.getValue()
                            .concat(pathVariable)
                            .concat(ResolveConstant.CLOSE_CURL.getValue()))
                    .collect(Collectors.joining(ResolveConstant.SPACE.getValue())));
    }
}

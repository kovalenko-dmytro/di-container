package core.application.resolve.console;

import core.application.input.console.entity.ConsoleRequest;
import core.application.resolve.Resolver;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.annotation.RequestMapping;
import core.application.resolve.entity.RequestPathMatchResult;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.ApplicationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsoleControllerResolver implements Resolver<ConsoleRequest> {

    private static final String SPACE = " ";
    private static final String OPEN_CURL = "{";
    private static final String CLOSE_CURL = "}";

    @Override
    public void resolve(ConsoleRequest request) throws ApplicationException {
        BeanFactory beanFactory = BeanFactory.getInstance();
        List<Object> controllers = beanFactory.getControllers();
        RequestPathMatchResult result = findRequestPathMatch(controllers, request);
        Method pathMatchMethod = result.getRequestPathMethod();
        try {
            pathMatchMethod.invoke(result.getController(), mapRequestParameters(pathMatchMethod, request));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ApplicationException(ErrorMessage.CANNOT_INVOKE_CONTROLLER_METHOD.getValue() + result.getRequestPathMethod().getName());
        }
    }

    private RequestPathMatchResult findRequestPathMatch(List<Object> controllers, ConsoleRequest request) throws ApplicationException {
        String requestPath = resolveRequestPath(request);
        for (Object controller: controllers) {
            for (Method method: controller.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)
                    && method.getAnnotation(RequestMapping.class).path().equalsIgnoreCase(requestPath)) {
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

    private String resolveRequestPath(ConsoleRequest request) {
        return request.getRequestPath()
            .concat(SPACE)
            .concat(
                request.getRequestParameters().keySet()
                    .stream()
                    .map(pathVariable -> OPEN_CURL + pathVariable + CLOSE_CURL)
                    .collect(Collectors.joining(SPACE)));
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
                        .orElseThrow(() -> new ApplicationException(ErrorMessage.CANNOT_RESOLVE_PATH_VARIABLE.getValue() + name)));
            }
        }
        return result.toArray();
    }
}

package core.application.resolve.console;

import core.application.exception.ApplicationException;
import core.application.info.ApiInfo;
import core.application.info.console.ConsoleApiInfo;
import core.application.input.entity.ConsoleRequest;
import core.application.resolve.Resolver;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.annotation.RequestMapping;
import core.application.resolve.constant.ResolveConstant;
import core.application.resolve.entity.RequestPathMatchResult;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsoleControllerResolver implements Resolver<ConsoleRequest> {

    private ApiInfo<ConsoleRequest> apiInfo;

    public ConsoleControllerResolver() {
        apiInfo = new ConsoleApiInfo();
    }

    @Override
    public void resolve(ConsoleRequest request) throws ApplicationException, BeanCreationException {
        if (ResolveConstant.HELP_REQUEST.getValue().equalsIgnoreCase(request.getRequestPath())) {
            apiInfo.getInfo(request);
            return;
        }
        List<Object> controllers = BeanFactory.getInstance().getControllers();
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

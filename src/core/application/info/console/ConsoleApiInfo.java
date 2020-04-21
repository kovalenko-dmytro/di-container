package core.application.info.console;

import core.application.info.ApiInfo;
import core.application.info.annotation.OperationInfo;
import core.application.info.annotation.OperationParam;
import core.application.info.constant.ApiInfoConstant;
import core.application.resolve.annotation.RequestMapping;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleApiInfo implements ApiInfo {

    @Override
    public void getInfo() throws BeanCreationException {
        List<Method> methods = findRequestMappingMethods();
        methods.forEach(method -> {
            System.out.println(ApiInfoConstant.API_DELIMITER.getValue());
            if (method.isAnnotationPresent(OperationInfo.class)) {
                getDeclaredInfo(method);
            } else {
                getDefaultInfo(method);
            }
        });
        System.out.println(ApiInfoConstant.API_DELIMITER.getValue());
    }

    private void getDeclaredInfo(Method method) {
        OperationInfo operationInfo = method.getAnnotation(OperationInfo.class);
        System.out.println(ApiInfoConstant.API_DECLARATION.getValue().concat(operationInfo.api()));
        System.out.println(ApiInfoConstant.API_DESCRIPTION.getValue().concat(operationInfo.description()));
        getParamsDeclaredInfo(operationInfo);
    }

    private void getParamsDeclaredInfo(OperationInfo operationInfo) {
        OperationParam[] operationParams = operationInfo.values().value();
        Arrays.stream(operationParams).forEach(operationParam -> System.out.println(
            ApiInfoConstant.API_PATH_VAR_DECLARATION.getValue()
                .concat(operationParam.name())
                .concat(ApiInfoConstant.API_PATH_VAR_DESCRIPTION.getValue())
                .concat(operationParam.description())));
    }

    private void getDefaultInfo(Method method) {
        System.out.println(ApiInfoConstant.API_DECLARATION.getValue().concat(method.getAnnotation(RequestMapping.class).path()));
        System.out.println(ApiInfoConstant.API_DEFAULT_DESCRIPTION.getValue());
    }

    private List<Method> findRequestMappingMethods() throws BeanCreationException {
        return BeanFactory.getInstance().getControllers().stream()
            .flatMap(controller ->
                Arrays.stream(controller.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class)))
            .collect(Collectors.toList());
    }
}

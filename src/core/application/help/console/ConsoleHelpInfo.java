package core.application.help.console;

import core.application.help.HelpInfo;
import core.application.help.annotation.HelpOperation;
import core.application.input.entity.ConsoleRequest;
import core.application.resolve.annotation.RequestMapping;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.Method;

public class ConsoleHelpInfo implements HelpInfo<ConsoleRequest> {

    @Override
    public void getInfo(ConsoleRequest request) throws BeanCreationException {
        if (request.getRequestParameters().isEmpty()) {
            getCommonInfo();
        } else {
            getCertainPathInfo(request);
        }
    }

    private void getCommonInfo() throws BeanCreationException {
        System.out.println("---------------Available APIs---------------");
        for (Object controller: BeanFactory.getInstance().getControllers()) {
            for (Method method: controller.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    if (method.isAnnotationPresent(HelpOperation.class)) {
                        HelpOperation helpOperation = method.getAnnotation(HelpOperation.class);
                        System.out.println("API: " + helpOperation.api());
                        System.out.println("--description: " + helpOperation.description());
                    } else {
                        System.out.println("API: " + method.getAnnotation(RequestMapping.class).path());
                        System.out.println("--info isn't available");
                    }
                    System.out.println("----------------------------------------------------");
                }
            }
        }
        System.out.println("---For detail API info enter 'help -<api>");
    }

    private void getCertainPathInfo(ConsoleRequest request) {

    }
}

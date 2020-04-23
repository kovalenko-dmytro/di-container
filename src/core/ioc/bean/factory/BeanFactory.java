package core.ioc.bean.factory;

import core.ioc.bean.factory.stereotype.Controller;
import core.ioc.bean.injector.BeanInjector;
import core.ioc.bean.scanner.BeanScanner;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanFactory {

    private BeanScanner beanScanner;
    private BeanInjector beanInjector;

    private Map<String, Object> beans = new HashMap<>();

    private BeanFactory() {
        beanScanner = new BeanScanner();
        beanInjector = new BeanInjector();
    }

    private static class BeanFactoryHolder {
        private static final BeanFactory instance = new BeanFactory();
    }

    public static BeanFactory getInstance() {
        return BeanFactoryHolder.instance;
    }

    public void init(String packageName) throws BeanCreationException {
        beanScanner.scanPackage(packageName, beans);
        beanInjector.injectDependencies(beans);
    }

    public List<Object> getControllers() throws BeanCreationException {
        checkInitBeans();
        return beans.values()
            .stream()
            .filter(bean -> bean.getClass().isAnnotationPresent(Controller.class))
            .collect(Collectors.toList());
    }

    private void checkInitBeans() throws BeanCreationException {
        if (beans.isEmpty()) {
            throw new BeanCreationException(ErrorMessage.ANY_BEAN_NOT_FOUND.getValue());
        }
    }
}

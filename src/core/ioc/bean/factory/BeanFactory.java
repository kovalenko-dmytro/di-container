package core.ioc.bean.factory;

import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.bean.injector.BeanInjector;
import core.ioc.bean.scanner.BeanScanner;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanFactory {

    private Map<String, Object> singletons;

    private BeanFactory() {}

    private static class BeanFactoryHolder {
        private static final BeanFactory instance = new BeanFactory();
    }

    public static BeanFactory getInstance() {
        return BeanFactoryHolder.instance;
    }

    public void init(String packageName) throws BeanCreationException {
        singletons = BeanScanner.scanPackage(packageName);
        BeanInjector.injectDependencies(singletons);
    }

    public Object getBean(String beanName) throws BeanCreationException {
        if (singletons.isEmpty()) {
            throw new BeanCreationException(ErrorMessage.ANY_BEAN_NOT_FOUND.getValue());
        }
        return singletons.get(beanName);
    }

    public List<Object> getLaunchBeans() {
        return singletons.values()
            .stream()
            .filter(singleton -> singleton.getClass().isAnnotationPresent(Launcher.class))
            .collect(Collectors.toList());
    }
}

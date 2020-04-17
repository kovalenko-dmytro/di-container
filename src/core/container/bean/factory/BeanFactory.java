package core.container.bean.factory;

import core.container.bean.factory.annotation.Autowired;
import core.container.bean.factory.stereotype.Launcher;
import core.container.bean.scanner.BeanScanner;
import core.container.constant.ContainerConstant;
import core.container.constant.ErrorMessage;
import core.container.exception.BeanCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        populateProperties();
    }

    private void populateProperties() throws BeanCreationException {
        singletons.values().forEach(bean -> {
            Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
                if (field.isAnnotationPresent(Autowired.class)) {
                    if (field.getType().isInterface()) {
                        List<Object> dependencies = singletons.values()
                            .stream()
                            .filter(impl ->
                                Arrays.stream(impl.getClass().getInterfaces())
                                    .anyMatch(interfaceClass ->
                                        interfaceClass.equals(field.getType())))
                            .collect(Collectors.toList());
                        if (dependencies.size() > 1 && field.getAnnotation(Autowired.class).fullQualifier().isBlank()) {
                            try {
                                throw new BeanCreationException(ErrorMessage.CANNOT_FIND_QUALIFIER.getValue());
                            } catch (BeanCreationException e) {
                                e.printStackTrace();
                            }
                        }
                        Optional<Object> dependency = dependencies
                            .stream()
                            .filter(impl -> field.getAnnotation(Autowired.class).fullQualifier().equals(impl.getClass().getName()))
                            .findFirst();

                        if (dependency.isEmpty()) {
                            try {
                                throw new BeanCreationException(ErrorMessage.CANNOT_INJECT_DEPENDENCY.getValue());
                            } catch (BeanCreationException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String setterName = ContainerConstant.SETTER_PREFIX.getValue()
                                .concat(field.getName().substring(0, 1).toUpperCase())
                                .concat(field.getName().substring(1));
                            Method setter;
                            try {
                                setter = bean.getClass().getMethod(setterName, field.getType());
                                setter.invoke(bean, dependency.get());
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        singletons.values().forEach(dependency -> {
                            if (dependency.getClass().equals(field.getType())) {
                                String setterName = ContainerConstant.SETTER_PREFIX.getValue()
                                    .concat(field.getName().substring(0, 1).toUpperCase())
                                    .concat(field.getName().substring(1));
                                Method setter;
                                try {
                                    setter = bean.getClass().getMethod(setterName, dependency.getClass());
                                    setter.invoke(bean, dependency);
                                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        });
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

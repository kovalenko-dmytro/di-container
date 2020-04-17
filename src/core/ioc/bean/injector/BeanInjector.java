package core.ioc.bean.injector;

import core.ioc.bean.factory.annotation.Autowired;
import core.ioc.constant.ContainerConstant;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeanInjector {

    public static void injectDependencies(Map<String, Object> beans) throws BeanCreationException {
        for (Object bean: beans.values()) {
            for (Field field: bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    if (field.getType().isInterface()) {
                        findFieldQualifierDependency(beans, bean, field);
                    } else {
                        findFieldDependency(beans, bean, field);
                    }
                }
            }
        }
    }

    private static void findFieldQualifierDependency(Map<String, Object> beans, Object bean, Field field) throws BeanCreationException {
        List<Object> dependencies = findFieldTypeBeanImplementations(beans, field);
        if (dependencies.size() > 1 && field.getAnnotation(Autowired.class).fullQualifier().isBlank()) {
            throw new BeanCreationException(ErrorMessage.CANNOT_FIND_QUALIFIER.getValue());
        }
        Optional<Object> dependency = findQualifierBeanImplementation(field, dependencies);
        if (dependency.isEmpty()) {
            throw new BeanCreationException(ErrorMessage.CANNOT_INJECT_DEPENDENCY.getValue());
        }
        injectBeanDependency(bean, field, dependency.get());
    }

    private static List<Object> findFieldTypeBeanImplementations(Map<String, Object> beans, Field field) {
        return beans.values().stream()
            .filter(impl ->
                Arrays.stream(impl.getClass().getInterfaces())
                    .anyMatch(interfaceClass -> interfaceClass.equals(field.getType())))
            .collect(Collectors.toList());
    }

    private static Optional<Object> findQualifierBeanImplementation(Field field, List<Object> dependencies) {
        return dependencies
            .stream()
            .filter(impl -> field.getAnnotation(Autowired.class).fullQualifier().equals(impl.getClass().getName()))
            .findFirst();
    }

    private static void findFieldDependency(Map<String, Object> beans, Object bean, Field field) throws BeanCreationException {
        for (Object dependency: beans.values()) {
            if (dependency.getClass().equals(field.getType())) {
                injectBeanDependency(bean, field, dependency);
            }
        }
    }

    private static void injectBeanDependency(Object bean, Field field, Object dependency) throws BeanCreationException {
        String setterName = ContainerConstant.SETTER_PREFIX.getValue()
            .concat(field.getName().substring(0, 1).toUpperCase())
            .concat(field.getName().substring(1));
        Method setter;
        try {
            setter = bean.getClass().getMethod(setterName, field.getType());
            setter.invoke(bean, dependency);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_INJECT_DEPENDENCY.getValue());
        }
    }
}

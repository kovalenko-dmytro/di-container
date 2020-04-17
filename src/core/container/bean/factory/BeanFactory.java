package core.container.bean.factory;

import core.container.bean.factory.annotation.Autowired;
import core.container.bean.factory.stereotype.ConsoleController;
import core.container.bean.factory.stereotype.Launcher;
import core.container.bean.factory.stereotype.Service;
import core.container.constant.ContainerConstant;
import core.container.constant.ErrorMessage;
import core.container.exception.BeanCreationException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private Map<String, Object> singletons = new HashMap<>();

    private BeanFactory() {}

    private static class BeanFactoryHolder {
        private static final BeanFactory instance = new BeanFactory();
    }

    public static BeanFactory getInstance() {
        return BeanFactoryHolder.instance;
    }

    public void init(String packageName) throws BeanCreationException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        scanPackage(packageName, classLoader);
        populateProperties();
    }

    private void scanPackage(String packageName, ClassLoader classLoader) throws BeanCreationException {
        String path = packageName.replace(ContainerConstant.DOT.getValue(), ContainerConstant.SLASH.getValue());
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());
                if (file.isDirectory()) {
                    for (File classFile : Objects.requireNonNull(file.listFiles())) {
                        processFile(packageName, classFile);
                    }
                }
            }
            System.out.println(singletons);
        } catch (IOException | URISyntaxException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_LOAD_PACKAGE.getValue());
        }
    }

    private void processFile(String packageName, File classFile) throws BeanCreationException {
        String fileName = classFile.getName();
        if (classFile.isFile() && fileName.endsWith(ContainerConstant.CLASS_EXTENSION.getValue())) {
            initBean(packageName, fileName);
        } else if (classFile.isDirectory()) {
            packageName = packageName.concat(ContainerConstant.DOT.getValue()).concat(fileName);
            for (File file : Objects.requireNonNull(classFile.listFiles())) {
                processFile(packageName, file);
            }
        }
    }

    private void initBean(String packageName, String fileName) throws BeanCreationException {
        String className = fileName.substring(0, fileName.lastIndexOf(ContainerConstant.DOT.getValue()));
        Class classObject;
        try {
            classObject = Class.forName(packageName.concat(ContainerConstant.DOT.getValue()).concat(className));
            if (classObject.isAnnotationPresent(Service.class) || classObject.isAnnotationPresent(ConsoleController.class) || classObject.isAnnotationPresent(Launcher.class)) {
                Object instance = classObject.getDeclaredConstructor().newInstance();
                String beanName = className.substring(0, 1).toLowerCase().concat(className.substring(1));
                singletons.put(beanName, instance);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_CREATE_BEAN.getValue() + packageName + fileName);
        }
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

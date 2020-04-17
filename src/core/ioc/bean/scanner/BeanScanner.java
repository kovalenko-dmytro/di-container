package core.ioc.bean.scanner;

import core.ioc.bean.factory.stereotype.ConsoleController;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.bean.factory.stereotype.Service;
import core.ioc.constant.ContainerConstant;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

public class BeanScanner {

    public void scanPackage(String packageName, Map<String, Object> beans) throws BeanCreationException {
        try {
            analyzeResources(packageName, beans);
        } catch (IOException | URISyntaxException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_LOAD_PACKAGE.getValue());
        }
    }

    private void analyzeResources(String packageName, Map<String, Object> beans) throws IOException, URISyntaxException, BeanCreationException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = packageName.replace(ContainerConstant.DOT.getValue(), ContainerConstant.SLASH.getValue());
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.toURI());
            if (file.isDirectory()) {
                for (File classFile : Objects.requireNonNull(file.listFiles())) {
                    processFile(packageName, classFile, beans);
                }
            }
        }
    }

    private void processFile(String packageName, File classFile, Map<String, Object> beans) throws BeanCreationException {
        String fileName = classFile.getName();
        if (classFile.isFile() && fileName.endsWith(ContainerConstant.CLASS_EXTENSION.getValue())) {
            initBean(packageName, fileName, beans);
        } else if (classFile.isDirectory()) {
            packageName = packageName.concat(ContainerConstant.DOT.getValue()).concat(fileName);
            for (File file : Objects.requireNonNull(classFile.listFiles())) {
                processFile(packageName, file, beans);
            }
        }
    }

    private void initBean(String packageName, String fileName, Map<String, Object> beans) throws BeanCreationException {
        String className = fileName.substring(0, fileName.lastIndexOf(ContainerConstant.DOT.getValue()));
        Class classObject;
        try {
            classObject = Class.forName(packageName.concat(ContainerConstant.DOT.getValue()).concat(className));
            if (classObject.isAnnotationPresent(Service.class) || classObject.isAnnotationPresent(ConsoleController.class) || classObject.isAnnotationPresent(Launcher.class)) {
                Object instance = classObject.getDeclaredConstructor().newInstance();
                String beanName = className.substring(0, 1).toLowerCase().concat(className.substring(1));
                beans.put(beanName, instance);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_CREATE_BEAN.getValue() + packageName + fileName);
        }
    }
}

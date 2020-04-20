package core.application;

import core.application.factory.ApplicationFactory;
import core.application.factory.Runner;
import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.ApplicationException;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Application {

    private static BeanFactory beanFactory;

    public static void launch(Class clazz, String... args) throws BeanCreationException, ApplicationException {
        instantiateBeans(clazz);
        Object launcher = getCurrentLauncher(clazz);
        //invokeLauncherStartMethod(launcher, args);

        Runner runner = ApplicationFactory.getRunner(launcher.getClass().getAnnotation(Launcher.class).launchType());
        runner.run(args);
    }

    private static Object getCurrentLauncher(Class clazz) throws BeanCreationException {
        List<Object> launchers = findLaunchers(clazz);
        if (launchers.size() > 1) {
            throw new BeanCreationException(ErrorMessage.CANNOT_MORE_THAN_ONE_LAUNCHER.getValue());
        }
        return launchers.get(0);
    }

    private static List<Object> findLaunchers(Class clazz) {
        return beanFactory.getLaunchBeans().stream()
            .filter(bean ->
                bean.getClass().getAnnotation(Launcher.class).launchType()
                    .equals(((Launcher) clazz.getAnnotation(Launcher.class)).launchType()))
            .collect(Collectors.toList());
    }

    private static void invokeLauncherStartMethod(Object launcher, Object args) throws BeanCreationException {
        String launchMethodName = launcher.getClass().getAnnotation(Launcher.class).launchType().getLaunchMethodName();
        Method launchMethod;
        try {
            launchMethod = launcher.getClass().getMethod(launchMethodName, String[].class);
            launchMethod.invoke(launcher, args);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_INVOKE_LAUNCH_METHOD.getValue());
        }
    }

    private static void instantiateBeans(Class clazz) throws BeanCreationException {
        String scanPackage = getScanPackageAttribute(clazz);
        beanFactory = BeanFactory.getInstance();
        beanFactory.init(scanPackage);
    }

    private static String getScanPackageAttribute(Class clazz) throws BeanCreationException {
        if (clazz.isAnnotationPresent(ScanPackage.class)) {
            ScanPackage scanPackage = (ScanPackage) clazz.getAnnotation(ScanPackage.class);
            return scanPackage.scanPackage().isBlank() ? clazz.getPackageName() : scanPackage.scanPackage();
        }
        throw new BeanCreationException(ErrorMessage.CANNOT_SCAN_PACKAGE.getValue());
    }
}

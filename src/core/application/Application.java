package core.application;

import core.application.exception.ApplicationException;
import core.application.factory.ApplicationFactory;
import core.application.factory.Runner;
import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.ErrorMessage;
import core.ioc.exception.BeanCreationException;

import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private Application(){}

    public static void launch(Class clazz, String... args) {
        try {
            instantiateBeans(clazz);
            Object launcher = getCurrentLauncher(clazz);
            Runner runner = ApplicationFactory.getRunner(launcher.getClass().getAnnotation(Launcher.class).launchType());
            runner.run(args);
        } catch (ApplicationException | BeanCreationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Object getCurrentLauncher(Class clazz) throws BeanCreationException {
        List<Object> launchers = findLaunchers(clazz);
        if (launchers.size() > 1) {
            throw new BeanCreationException(ErrorMessage.CANNOT_MORE_THAN_ONE_LAUNCHER.getValue());
        }
        return launchers.get(0);
    }

    private static List<Object> findLaunchers(Class clazz) throws BeanCreationException {
        return BeanFactory.getInstance().getLaunchBeans().stream()
            .filter(bean ->
                bean.getClass().getAnnotation(Launcher.class).launchType()
                    .equals(((Launcher) clazz.getAnnotation(Launcher.class)).launchType()))
            .collect(Collectors.toList());
    }

    private static void instantiateBeans(Class clazz) throws BeanCreationException {
        String scanPackage = getScanPackageAttribute(clazz);
        BeanFactory.getInstance().init(scanPackage);
    }

    private static String getScanPackageAttribute(Class clazz) throws BeanCreationException {
        if (clazz.isAnnotationPresent(ScanPackage.class)) {
            ScanPackage scanPackage = (ScanPackage) clazz.getAnnotation(ScanPackage.class);
            return scanPackage.scanPackage().isBlank() ? clazz.getPackageName() : scanPackage.scanPackage();
        }
        throw new BeanCreationException(ErrorMessage.CANNOT_SCAN_PACKAGE.getValue());
    }
}

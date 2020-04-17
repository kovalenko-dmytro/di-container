package core.application;

import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.BeanFactory;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.ErrorMessage;
import core.ioc.constant.LaunchType;
import core.ioc.exception.BeanCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Application {

    private static BeanFactory beanFactory;

    public abstract void start(String ... args);

    protected static void launch(Class clazz, String ... args) throws BeanCreationException {
        init(clazz);
        List<Object> launchers = beanFactory.getLaunchBeans().stream()
            .filter(bean ->
                bean.getClass().getAnnotation(Launcher.class).launchType()
                    .equals(((Launcher)clazz.getAnnotation(Launcher.class)).launchType()))
            .collect(Collectors.toList());
        if (launchers.size() > 1) {
            throw new BeanCreationException(ErrorMessage.CANNOT_MORE_THAN_ONE_LAUNCHER.getValue());
        }
        Object launcher = launchers.get(0);
        String launchMethodName = launcher.getClass().getAnnotation(Launcher.class).launchType().getLaunchMethodName();
        Method launchMethod;
        try {
            launchMethod = launcher.getClass().getMethod(launchMethodName, String[].class);
            launchMethod.invoke(launcher, (Object) args);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeanCreationException(ErrorMessage.CANNOT_INVOKE_LAUNCH_METHOD.getValue());
        }
    }

    private static void init(Class clazz) throws BeanCreationException {
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

    private static LaunchType getLauncherAttribute(Class clazz) throws BeanCreationException {
        if (clazz.isAnnotationPresent(Launcher.class)) {
            Launcher launcher = (Launcher) clazz.getAnnotation(Launcher.class);
            return launcher.launchType();
        }
        throw new BeanCreationException(ErrorMessage.CANNOT_FIND_LAUNCHER.getValue());
    }
}

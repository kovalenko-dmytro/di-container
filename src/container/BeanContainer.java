package container;

import container.annotation.ScanPackage;
import container.bean.factory.BeanFactory;
import container.constant.ErrorMessage;
import container.exception.BeanCreationException;

public class BeanContainer {

    public static void init(Class clazz) throws BeanCreationException {
        String scanPackage = getScanPackageAttribute(clazz);
        BeanFactory beanFactory = BeanFactory.getInstance();
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

package core.application.info;

import core.application.exception.ApplicationException;
import core.ioc.exception.BeanCreationException;

public interface ApiInfo<T> {
    void getInfo(T request) throws BeanCreationException, ApplicationException;
}

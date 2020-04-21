package core.application.info;

import core.application.exception.ApplicationException;
import core.ioc.exception.BeanCreationException;

public interface ApiInfo {
    void getInfo() throws BeanCreationException, ApplicationException;
}

package core.application.help;

import core.ioc.exception.BeanCreationException;

public interface HelpInfo<T> {
    void getInfo(T request) throws BeanCreationException;
}

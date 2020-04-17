package core.application;

import core.container.BeanContainer;
import core.container.exception.BeanCreationException;

public abstract class Application {

    public abstract void start(String ... args);

    protected static void launch(Class clazz, String ... args) throws BeanCreationException {
        BeanContainer.init(clazz);

        //init MAin object and invoke start method

    }
}

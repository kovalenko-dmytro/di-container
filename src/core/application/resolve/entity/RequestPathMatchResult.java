package core.application.resolve.entity;

import java.lang.reflect.Method;

public class RequestPathMatchResult {

    private Object controller;
    private Method requestPathMethod;
    private Class[] parameterTypes;

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getRequestPathMethod() {
        return requestPathMethod;
    }

    public void setRequestPathMethod(Method requestPathMethod) {
        this.requestPathMethod = requestPathMethod;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}

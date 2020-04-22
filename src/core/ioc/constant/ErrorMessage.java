package core.ioc.constant;

public enum ErrorMessage {
    ERROR_PREFIX("ERROR: "),
    CANNOT_LOAD_PACKAGE(ERROR_PREFIX.value + "Cannot load source package"),
    CANNOT_CREATE_BEAN(ERROR_PREFIX.value + "Cannot create bean:"),
    CANNOT_INJECT_DEPENDENCY(ERROR_PREFIX.value + "Cannot inject dependency for bean: "),
    CANNOT_FIND_QUALIFIER(ERROR_PREFIX.value + "Found several bean implementation. Cannot find bean qualifier"),
    CANNOT_SCAN_PACKAGE(ERROR_PREFIX.value + "Cannot scan package"),
    CANNOT_FIND_LAUNCHER(ERROR_PREFIX.value + "Cannot find launcher object"),
    ANY_BEAN_NOT_FOUND(ERROR_PREFIX.value + "Any bean not found"),
    CANNOT_MORE_THAN_ONE_LAUNCHER(ERROR_PREFIX.value + "Cannot be more than one launcher with the same launch type"),
    CANNOT_INVOKE_LAUNCH_METHOD(ERROR_PREFIX.value + "Cannot invoke launch method"),
    CANNOT_RESOLVE_LAUNCH_TYPE(ERROR_PREFIX.value + "Cannot resolve launch type"),
    CANNOT_PARSE_REQUEST_PATH(ERROR_PREFIX.value + "Cannot parse request path"),
    CANNOT_INVOKE_CONTROLLER_METHOD(ERROR_PREFIX.value + "Cannot invoke controller method: "),
    CANNOT_RESOLVE_REQUEST_PATH(ERROR_PREFIX.value + "Cannot resolve request path"),
    CANNOT_RESOLVE_PATH_VARIABLE(ERROR_PREFIX.value + "Cannot resolve path variable with name: {0} for request path: {1}"),
    CANNOT_FIND_CONSTRAINT_VALIDATOR(ERROR_PREFIX.value + "Cannot find constraint validator"),
    VALIDATE_MESSAGE(ERROR_PREFIX.value + "<{0}> path variable {1}");


    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

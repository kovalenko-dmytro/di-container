package core.container.constant;

public enum ErrorMessage {
    CANNOT_LOAD_PACKAGE("Cannot load source package"),
    CANNOT_CREATE_BEAN("Cannot create bean:"),
    CANNOT_INJECT_DEPENDENCY("Cannot inject dependency for bean: "),
    CANNOT_FIND_QUALIFIER("Found several bean implementation. Cannot find bean qualifier"),
    CANNOT_SCAN_PACKAGE("Cannot scan package"),
    CANNOT_FIND_LAUNCHER("Cannot find launcher object"),
    ANY_BEAN_NOT_FOUND("Any bean not found"),
    CANNOT_MORE_THAN_ONE_LAUNCHER("Cannot be more than one launcher with the same launch type"),
    CANNOT_INVOKE_LAUNCH_METHOD("Cannot invoke launch method");


    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

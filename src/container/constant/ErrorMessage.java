package container.constant;

public enum ErrorMessage {
    CANNOT_LOAD_PACKAGE("Cannot load source package"),
    CANNOT_CREATE_BEAN("Cannot create bean:"),
    CANNOT_INJECT_DEPENDENCY("Cannot inject dependency for bean: "),
    CANNOT_SCAN_PACKAGE("Cannot scan package"),
    ANY_BEAN_NOT_FOUND("Any bean not found");


    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

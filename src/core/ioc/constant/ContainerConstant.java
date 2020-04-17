package core.ioc.constant;

public enum ContainerConstant {
    DOT("."),
    SLASH("/"),
    CLASS_EXTENSION(".class"),
    SETTER_PREFIX("set");

    private String value;

    ContainerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

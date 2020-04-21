package core.application.resolve.constant;

public enum ResolveConstant {
    SPACE(" "),
    OPEN_CURL("{"),
    CLOSE_CURL("}");

    private String value;

    ResolveConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package core.application.input.constant;

public enum InputConstant {

    REQUEST_PATH_REGEX("^([^-]+)"),
    REQUEST_PARAMS_REGEX("(-\\S+)\\s*((\"(.*?)\")|([^-]\\S*))?"),
    NEW_COMMAND_START(">");


    private String value;

    InputConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package core.application.info.constant;

public enum ApiInfoConstant {
    API_DELIMITER("----------------------------------------------------"),
    API_DECLARATION("API: "),
    API_DESCRIPTION("--description: "),
    API_PATH_VAR_DECLARATION("----path variable: "),
    API_PATH_VAR_DESCRIPTION("; description: "),
    API_DEFAULT_DESCRIPTION("--Api info isn't available");

    private String value;

    ApiInfoConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

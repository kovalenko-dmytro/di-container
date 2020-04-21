package core.application.runner.constant;

public enum RunnerConstant {

    EXIT_COMMAND_NAME("exit"),
    INFO_COMMAND_NAME("info"),
    PLEASE_ENTER_REQUEST_COMMAND("-------- Please enter request command --------"),
    INFO_TO_VIEW_AVAILABLE_API_INFO("<" + INFO_COMMAND_NAME.getValue() + "> to view available API info"),
    EXIT_TO_EXIT_FROM_PROGRAM("<" + EXIT_COMMAND_NAME.getValue() + "> to exit from program");

    private String value;

    RunnerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package core.application.runner.constant;

import core.application.resolve.constant.ResolveConstant;

public enum RunnerConstant {

    EXIT_COMMAND_NAME("exit"),
    PLEASE_ENTER_REQUEST_COMMAND("-------- Please enter request command --------"),
    INFO_TO_VIEW_AVAILABLE_API_INFO("<" + ResolveConstant.HELP_REQUEST.getValue() + "> to view available API info"),
    EXIT_TO_EXIT_FROM_PROGRAM("<" + EXIT_COMMAND_NAME.getValue() + "> to exit from program");

    private String value;

    RunnerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

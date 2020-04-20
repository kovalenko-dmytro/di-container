package core.application.input.console.entity;

import java.util.Map;

public class ConsoleRequest {

    private String commandName;
    private Map<String, String> commandParameters;

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Map<String, String> getCommandParameters() {
        return commandParameters;
    }

    public void setCommandParameters(Map<String, String> commandParameters) {
        this.commandParameters = commandParameters;
    }
}

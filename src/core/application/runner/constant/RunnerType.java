package core.application.runner.constant;

import java.util.Arrays;

public enum RunnerType {

    CONSOLE("console"),
    SCRIPT("-script"),
    XML("-xml");

    private String value;

    RunnerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RunnerType getType(String name) {
        return Arrays.stream(RunnerType.values())
            .filter(type -> type.getValue().equalsIgnoreCase(name))
            .findFirst()
            .orElse(CONSOLE);
    }
}

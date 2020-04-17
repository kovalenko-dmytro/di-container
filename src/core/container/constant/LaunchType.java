package core.container.constant;

public enum LaunchType {

    CONSOLE("start");

    private String launchMethodName;

    LaunchType(String launchMethodName) {
        this.launchMethodName = launchMethodName;
    }

    public String getLaunchMethodName() {
        return launchMethodName;
    }
}

package client;


import core.application.Application;
import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.LaunchType;

@Launcher(launchType = LaunchType.CONSOLE)
@ScanPackage
public class Main {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

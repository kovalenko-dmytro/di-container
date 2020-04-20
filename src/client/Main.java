package client;


import core.application.Application;
import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.LaunchType;
import core.ioc.exception.ApplicationException;

@Launcher(launchType = LaunchType.CONSOLE)
@ScanPackage
public class Main {

    public static void main(String[] args) throws ApplicationException {
        Application.launch(Main.class, args);
    }
}

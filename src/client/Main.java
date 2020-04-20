package client;


import client.controller.CommandInterface;
import core.application.Application;
import core.ioc.annotation.ScanPackage;
import core.ioc.bean.factory.annotation.Autowired;
import core.ioc.bean.factory.stereotype.Launcher;
import core.ioc.constant.LaunchType;
import core.ioc.exception.ApplicationException;

@Launcher(launchType = LaunchType.CONSOLE)
@ScanPackage
public class Main {

    @Autowired(fullQualifier = "client.controller.ProductCommand")
    private CommandInterface productCommand;

    public static void main(String[] args) throws ApplicationException {
        Application.launch(Main.class, args);
    }

    public void setProductCommand(CommandInterface productCommand) {
        this.productCommand = productCommand;
    }
}

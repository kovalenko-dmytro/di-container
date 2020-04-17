package client;


import client.controller.CommandInterface;
import core.application.Application;
import core.container.annotation.ScanPackage;
import core.container.bean.factory.annotation.Autowired;
import core.container.bean.factory.stereotype.Launcher;
import core.container.exception.BeanCreationException;

@Launcher
@ScanPackage
public class Main extends Application {

    @Autowired(fullQualifier = "client.controller.ProductCommand")
    private CommandInterface productCommand;

    public static void main(String[] args) throws BeanCreationException {
        launch(Main.class, args);
    }

    @Override
    public void start(String... args) {
        productCommand.doCommand();
    }

    public void setProductCommand(CommandInterface productCommand) {
        this.productCommand = productCommand;
    }
}

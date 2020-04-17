package client;


import client.controller.ProductCommand;
import core.application.Application;
import core.container.annotation.ScanPackage;
import core.container.bean.factory.BeanFactory;
import core.container.exception.BeanCreationException;

@ScanPackage
public class Main extends Application {

    public static void main(String[] args) throws BeanCreationException {
        launch(Main.class, args);

        BeanFactory beanFactory = BeanFactory.getInstance();
        ProductCommand productCommand = (ProductCommand) beanFactory.getBean("productCommand");
        productCommand.doCommand();
    }

    @Override
    public void start(String... args) {

    }
}

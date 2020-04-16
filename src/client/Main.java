package client;

import client.controller.ProductCommand;
import container.BeanContainer;
import container.annotation.ScanPackage;
import container.bean.factory.BeanFactory;
import container.exception.BeanCreationException;

@ScanPackage
public class Main {

    public static void main(String[] args) throws BeanCreationException {
        BeanContainer.init(Main.class);
        BeanFactory beanFactory = BeanFactory.getInstance();
        ProductCommand productCommand = (ProductCommand) beanFactory.getBean("productCommand");
        productCommand.doCommand();
    }
}

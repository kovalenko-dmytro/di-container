package client;

import client.controller.ProductCommand;
import client.service.ProductService;
import container.bean.factory.BeanFactory;
import container.exception.BeanCreationException;

public class Main {

    public static void main(String[] args) throws BeanCreationException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.init("client");
        ProductCommand productCommand = (ProductCommand) beanFactory.getBean("productCommand");
        productCommand.doCommand();

    }
}

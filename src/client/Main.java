package client;

import client.service.ProductService;
import container.bean.factory.BeanFactory;
import container.exception.BeanCreationException;

public class Main {

    public static void main(String[] args) throws BeanCreationException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.init("client");
        ProductService productService = (ProductService)beanFactory.getBean("productService");
        System.out.println(productService);
        System.out.println(productService.getPromotionService());
        System.out.println();
        productService.toDoSomething();

    }
}

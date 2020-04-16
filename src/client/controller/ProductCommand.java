package client.controller;

import client.SourceType;
import client.service.ProductService;
import container.bean.factory.annotation.Autowired;
import container.bean.factory.stereotype.Command;
import container.bean.factory.stereotype.Service;

@Command
public class ProductCommand {

    @Autowired
    private ProductService productService;

    public void doCommand() {
        productService.toDoSomething();
    }

    public void setProductService(ProductService productService) {
        System.out.println("command start.....");
        this.productService = productService;
    }
}

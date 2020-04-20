package client.controller;

import client.service.ClientService;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.annotation.RequestMapping;
import core.ioc.bean.factory.annotation.Autowired;
import core.ioc.bean.factory.stereotype.Controller;

@Controller
public class ProductController {

    @Autowired(fullQualifier = "client.service.ProductClientService")
    private ClientService clientService;

    @RequestMapping(path = "analyze java {-file} {-source} {-target}")
    public void doCommand1(@PathVariable(name = "-file") String file,
                          @PathVariable(name = "-source") String source,
                          @PathVariable(name = "-target") String target) {
        System.out.println("command1 start.....");
        System.out.println(file);
        System.out.println(source);
        System.out.println(target);
        clientService.toDoSomething();
    }

    @RequestMapping(path = "analyze groovy {-file} {-source} {-target}")
    public void doCommand2(@PathVariable(name = "-file") String file,
                          @PathVariable(name = "-source") String source,
                          @PathVariable(name = "-target") String target) {
        System.out.println("command2 start.....");
        System.out.println(file);
        System.out.println(source);
        System.out.println(target);
        clientService.toDoSomething();
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
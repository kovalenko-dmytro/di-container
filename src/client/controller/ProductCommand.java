package client.controller;

import client.service.ClientService;
import core.container.bean.factory.annotation.Autowired;
import core.container.bean.factory.stereotype.ConsoleController;

@ConsoleController
public class ProductCommand {

    @Autowired(fullQualifier = "client.service.ProductClientService")
    private ClientService clientService;

    public void doCommand() {
        System.out.println("command start.....");
        clientService.toDoSomething();
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}

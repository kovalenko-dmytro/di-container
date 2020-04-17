package client.service;


import core.container.bean.factory.annotation.Autowired;
import core.container.bean.factory.stereotype.Service;

@Service
public class ProductClientService implements ClientService {

    @Autowired(fullQualifier = "client.service.PromotionClientService")
    private ClientService clientService;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void toDoSomething() {
        System.out.println("Start invoking productService.toDoSomething()");
        clientService.toDoSomething();
        System.out.println("Finish invoking productService.toDoSomething()");
    }
}

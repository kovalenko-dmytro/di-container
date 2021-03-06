package client.service;

import core.ioc.bean.factory.annotation.Autowired;
import core.ioc.bean.factory.stereotype.Service;

@Service
public class PromotionClientService implements ClientService {

    @Autowired(fullQualifier = "client.service.ActionClientService")
    private ClientService clientService;

    @Override
    public void toDoSomething() {
        System.out.println("Invoke promotionService.toDoSomething()");
        clientService.toDoSomething();
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}

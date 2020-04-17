package client.service;


import core.container.bean.factory.stereotype.Service;

@Service
public class ActionClientService implements ClientService {

    @Override
    public void toDoSomething() {
        System.out.println("Invoke actionService.toDoSomething()");
    }
}

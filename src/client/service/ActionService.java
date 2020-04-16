package client.service;

import container.bean.factory.stereotype.Service;

@Service
public class ActionService {

    public void toDoSomething() {
        System.out.println("Invoke actionService.toDoSomething()");
    }
}

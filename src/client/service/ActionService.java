package client.service;

import container.bean.factory.stereotype.Component;

@Component
public class ActionService {

    public void toDoSomething() {
        System.out.println("Invoke actionService.toDoSomething()");
    }
}

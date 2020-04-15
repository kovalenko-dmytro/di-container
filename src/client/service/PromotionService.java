package client.service;

import container.bean.factory.annotation.Autowired;
import container.bean.factory.stereotype.Component;

@Component
public class PromotionService {

    @Autowired
    private ActionService actionService;

    public void toDoSomething() {
        System.out.println("Invoke promotionService.toDoSomething()");
        actionService.toDoSomething();
    }

    public ActionService getActionService() {
        return actionService;
    }

    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }
}

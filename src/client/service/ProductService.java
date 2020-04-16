package client.service;

import container.bean.factory.annotation.Autowired;
import container.bean.factory.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private PromotionService promotionService;

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void toDoSomething() {
        System.out.println("Start invoking productService.toDoSomething()");
        promotionService.toDoSomething();
        System.out.println("Finish invoking productService.toDoSomething()");
    }
}

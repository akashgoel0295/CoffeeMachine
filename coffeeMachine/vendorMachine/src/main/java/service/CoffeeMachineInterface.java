package service;

import models.BeverageRequest;
import models.Inventory;
import models.RechargeRequest;

public interface CoffeeMachineInterface {
    void serveCoffee(BeverageRequest beverageRequest);
    void rechargeCoffee(RechargeRequest rechargeRequest);
}

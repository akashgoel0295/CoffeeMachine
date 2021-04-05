package service;

import models.Inventory;
import models.RechargeRequest;

import java.util.Map;

public interface InventoryInterface {

    Inventory getInventory();
    Inventory subtractInventory(Map<String,Integer>drinkIngredients);
    Inventory addInventory(RechargeRequest rechargeRequest);
    InventoryInterfaceImpl.IngredientStatusCheck checkIngredientsAvailableToServeRequest(Map<String,Integer> drinkIngredients);

}

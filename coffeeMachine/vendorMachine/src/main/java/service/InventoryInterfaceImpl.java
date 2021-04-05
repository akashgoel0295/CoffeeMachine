package service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Inventory;
import models.RechargeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryInterfaceImpl implements InventoryInterface {
    private static Inventory inventory = new Inventory();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Inventory subtractInventory(Map<String, Integer> drinkIngredients) {
        for (Map.Entry<String, Integer> entry : drinkIngredients.entrySet()) {
            Integer existingVal = inventory.getIngredients().get(entry.getKey());
            inventory.getIngredients().put(entry.getKey(), existingVal - entry.getValue());
        }
        return inventory;
    }

    @Override
    public Inventory addInventory(RechargeRequest rechargeRequest) {
        Map<String, Integer> itemsToAdd = rechargeRequest.getTotal_items_quantity();
        for (Map.Entry<String, Integer> entry : itemsToAdd.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (inventory.getIngredients().containsKey(key)) {
                Integer valueBefore = inventory.getIngredients().get(key);
                inventory.getIngredients().put(key, valueBefore + value);
            } else {
                inventory.getIngredients().put(key, value);
            }
        }
        return inventory;
    }

    @Override
    public IngredientStatusCheck checkIngredientsAvailableToServeRequest(Map<String, Integer> drinkIngredients) {
        IngredientStatusCheck ingredientStatusCheck=new IngredientStatusCheck();

        for(Map.Entry<String,Integer>entry:drinkIngredients.entrySet()){
            if(!inventory.getIngredients().containsKey(entry.getKey())){
                ingredientStatusCheck.setStatus(false);
                ingredientStatusCheck.getMissingIngredients().add(entry.getKey());
            }
            else if(inventory.getIngredients().get(entry.getKey())< entry.getValue()){
                ingredientStatusCheck.setStatus(false);
                ingredientStatusCheck.getInsufficientIngredients().add(entry.getKey());
            }
        }
        return ingredientStatusCheck;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientStatusCheck {
        private boolean status = true;
        private List<String> missingIngredients = new ArrayList<>();
        private List<String> insufficientIngredients = new ArrayList<>();
    }
}

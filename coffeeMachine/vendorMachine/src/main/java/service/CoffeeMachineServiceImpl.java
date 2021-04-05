package service;

import models.BeverageRequest;
import models.RechargeRequest;
import models.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoffeeMachineServiceImpl implements CoffeeMachineInterface {
    private static Integer outlets = null;
    private InventoryInterface inventoryService=new InventoryInterfaceImpl();

    public CoffeeMachineServiceImpl(Request request) {
        outlets = request.getMachine().getOutlets().getCount_n();
        RechargeRequest rechargeRequest=new RechargeRequest();
        rechargeRequest.setTotal_items_quantity(request.getMachine().total_items_quantity);
        inventoryService.addInventory(rechargeRequest);
    }


    @Override
    public void serveCoffee(BeverageRequest beverageRequest) {
        Map<String, Map<String, Integer>> drinkList = beverageRequest.getBeverages();
        /*
        Assuming that the request payload is at a particular instant.So we will process only those number of requests at a time
        which is equal to the number of outlets.Once all those requests are done,we will process the further waiting requests
        (Assumption-Time taken to process each request is the same)
         */
        Integer cnt = 0;
        List<Map.Entry<String,Map<String,Integer>>> beverageItems=new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : drinkList.entrySet()) {
            beverageItems.add(entry);
            cnt=(cnt+1)%outlets;
            if(cnt==0){
                fulfillBeverageRequest(beverageItems);
                beverageItems.clear();
            }
        }
        //remaining items;
        fulfillBeverageRequest(beverageItems);
        beverageItems.clear();
    }

    private void fulfillBeverageRequest(List<Map.Entry<String, Map<String, Integer>>> beverageItems) {
        for (Map.Entry<String, Map<String, Integer>> item : beverageItems) {
            String drinkName = item.getKey();
            Map<String, Integer> drinkIngredients = item.getValue();
            InventoryInterfaceImpl.IngredientStatusCheck statusCheck = inventoryService.checkIngredientsAvailableToServeRequest(drinkIngredients);
            if (statusCheck.isStatus()) {
                inventoryService.subtractInventory(drinkIngredients);
                printSuccessMessage(drinkName);
            } else {
                printFailureMessage(drinkName, statusCheck.getMissingIngredients(), statusCheck.getInsufficientIngredients());
            }
        }
    }

    private  void printSuccessMessage(String drinkName) {
        String message=String.format("%s is prepared",drinkName);
        System.out.println(message);
    }

    private  void printFailureMessage(String drinkName, List<String> missingIngredientsList, List<String> insufficientIngredients) {
        String message = String.format("%s cannot be prepared because ", drinkName);
        /*
        Printing either the missing or insufficient ingredient list.
         */
        if (missingIngredientsList.size() != 0) {
            for (String missingIngredient : missingIngredientsList) {
                message = message + missingIngredient + " ";
            }
            message = message + "is not available";
        } else {
            for (String insufficientIngredient : insufficientIngredients) {
                message = message + insufficientIngredient + " ";
            }
            message = message + "is not sufficient";
        }
        System.out.println(message);
    }

    @Override
    public void rechargeCoffee(RechargeRequest rechargeRequest) {
        inventoryService.addInventory(rechargeRequest);
    }
}

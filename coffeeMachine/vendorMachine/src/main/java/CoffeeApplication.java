import models.BeverageRequest;
import models.RechargeRequest;
import models.Request;
import service.CoffeeMachineInterface;
import service.CoffeeMachineServiceImpl;
import utilities.FileLoad;

public class CoffeeApplication {

    private static FileLoad fileLoad=new FileLoad();
    public static void main(String args[]){

        /*
        We can load any of the file present in the resources folder
         */

        Request request=fileLoad.getRequestPojo("InitialRequest.json");

        /*
        Initialise the initial state of the coffee machine(the ingredients available and the number of outlets)
         */
        CoffeeMachineInterface coffeeMachineService=new CoffeeMachineServiceImpl(request);

        /*
        Serve the request
         */

        BeverageRequest beverageRequest=new BeverageRequest();
        beverageRequest.setBeverages(request.getMachine().getBeverages());
        coffeeMachineService.serveCoffee(beverageRequest);

        /*
        Recharge the coffeeMachine
         */

        RechargeRequest rechargeRequest=fileLoad.getRechargeRequest("rechargeRequest.json");
        coffeeMachineService.rechargeCoffee(rechargeRequest);

        /*
        Serve again after recharging
         */

        BeverageRequest requestAfterRecharge=fileLoad.getBeverageRequest("serveRequestAfterRecharge.json");
        coffeeMachineService.serveCoffee(requestAfterRecharge);

    }
}

package cielsachen.ccprog3.mco2;

import java.util.ArrayList;
import java.util.List;

import cielsachen.ccprog3.mco2.controller.CoffeeController;
import cielsachen.ccprog3.mco2.controller.MenuController;
import cielsachen.ccprog3.mco2.controller.TruckController;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.service.TruckService;

/** Represents the entry point for Java programs. */
public class Main {
    /**
     * Executes the Java program.
     *
     * @param args The CLI arguments.
     */
    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        List<StorageBin> storageBins = new ArrayList<StorageBin>();
        List<Truck> trucks = new ArrayList<Truck>();

        var transactionService = new TransactionService(transactions);
        var storageBinService = new StorageBinService(storageBins);
        var coffeeService = new CoffeeService(storageBinService);
        var truckService = new TruckService(trucks);

        var coffeeController = new CoffeeController(coffeeService, storageBinService, transactionService);
        var truckController = new TruckController(coffeeController, truckService, storageBinService, coffeeService);
        var menuController = new MenuController(truckController, coffeeController, truckService, storageBinService,
                coffeeService, transactionService);

        menuController.showMainMenu();
    }
}

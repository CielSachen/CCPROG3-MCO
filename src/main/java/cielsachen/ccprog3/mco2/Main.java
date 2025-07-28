package cielsachen.ccprog3.mco2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.mco2.controller.CoffeeController;
import cielsachen.ccprog3.mco2.controller.MenuController;
import cielsachen.ccprog3.mco2.controller.TransactionController;
import cielsachen.ccprog3.mco2.controller.TruckController;
import cielsachen.ccprog3.mco2.helper.Input;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.service.TruckService;

/** Represents the entry point class for Java programs. */
public class Main {
    /**
     * Executes the Java program.
     *
     * @param arguments The CLI arguments.
     */
    public static void main(String[] arguments) {
        var scanner = new Scanner(System.in);
        var input = new Input(scanner);

        List<Transaction> transactions = new ArrayList<Transaction>();
        List<StorageBin> storageBins = new ArrayList<StorageBin>();
        List<Truck> trucks = new ArrayList<Truck>();

        var transactionService = new TransactionService(transactions);
        var storageBinService = new StorageBinService(storageBins);
        var coffeeService = new CoffeeService(storageBinService);
        var truckService = new TruckService(trucks);

        var transactionController = new TransactionController(transactionService);
        var coffeeController = new CoffeeController(coffeeService, storageBinService, transactionService, scanner,
                input);
        var truckController = new TruckController(coffeeController, truckService, storageBinService, scanner, input);
        var menuController = new MenuController(truckController, coffeeController, transactionController);

        menuController.showMainMenu();
    }
}

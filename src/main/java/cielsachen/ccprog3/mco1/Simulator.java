package cielsachen.ccprog3.mco1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.mco1.controller.CoffeeController;
import cielsachen.ccprog3.mco1.controller.TransactionController;
import cielsachen.ccprog3.mco1.controller.TruckController;
import cielsachen.ccprog3.mco1.helper.ExceptionMessage;
import cielsachen.ccprog3.mco1.helper.Input;
import cielsachen.ccprog3.mco1.helper.Output;
import cielsachen.ccprog3.mco1.helper.PrintColor;
import cielsachen.ccprog3.mco1.model.StorageBin;
import cielsachen.ccprog3.mco1.model.Transaction;
import cielsachen.ccprog3.mco1.model.Truck;
import cielsachen.ccprog3.mco1.service.CoffeeService;
import cielsachen.ccprog3.mco1.service.StorageBinService;
import cielsachen.ccprog3.mco1.service.TransactionService;
import cielsachen.ccprog3.mco1.service.TruckService;

/** Represents the simulator program. */
public class Simulator {
    /** Creates a new {@code Simulator} object instance. */
    public Simulator() {
    }

    /** Launches the simulator. */
    public void launch() {
        boolean isExiting = false;

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
        var truckController = new TruckController(truckService, storageBinService, scanner, input);

        while (!isExiting) {
            Output.printTitle("Coffee Truck Manager Simulator: MCO1");

            System.out.println();

            System.out.println("What would you like to do?");
            System.out.println("  [C] Create a Coffee truck");
            System.out.println("  [S] Simulate");
            System.out.println("  [D] Dashboard");
            System.out.println();
            System.out.println("  [X] Exit");

            System.out.println();

            System.out.print("  > ");

            char chosenOptionId = input.getCharacter();

            switch (chosenOptionId) {
                case 'C': {
                    System.out.println();

                    Truck truck = truckController.createTruck();

                    System.out.println();

                    truckController.printTruckInfo(truck);

                    if (!coffeeController.isPricesSet() || input.getBoolean("Do you want to update the prices "
                            + PrintColor.set("(true/false)", PrintColor.RED) + "?", true)) {
                        coffeeController.updatePrices();
                    }

                    break;
                }
                case 'S': {
                    if (truckService.getTrucks().size() == 0) {
                        System.out.println();

                        ExceptionMessage.printCustom("You have not yet created any trucks!");

                        break;
                    }

                    Truck chosenTruck = null;

                    while (true) {
                        try {
                            System.out.println();

                            System.out.println("Which truck would you like to view?");

                            for (Truck truck : truckService.getTrucks()) {
                                System.out.println("  [" + truck.id + "] Located at: "
                                        + PrintColor.set(truck.getLocation(), PrintColor.BRIGHT_CYAN));
                            }

                            System.out.println();
                            System.out.println("  [X] Return");

                            System.out.println();

                            System.out.print("  > ");

                            int chosenTruckId = scanner.nextInt();

                            scanner.nextLine();

                            if (chosenTruckId > 0 && chosenTruckId <= truckService.getTrucks().size()) {
                                chosenTruck = truckService.getTruckById(chosenTruckId).get();

                                break;
                            } else {
                                System.out.println();

                                ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                            }
                        } catch (InputMismatchException exception) {
                            if (input.getCharacter() == 'X') {
                                break;
                            }

                            System.out.println();

                            ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                        }
                    }

                    if (chosenTruck == null) {
                        break;
                    }

                    while (true) {
                        try {
                            System.out.println();

                            System.out.println("Which feature would you like to simulate?");
                            System.out.println("  [S] Sale and Preparation of the Coffee Drink");
                            System.out.println("  [V] View Truck Information");
                            System.out.println("  [R] Restocking of Storage Bins");
                            System.out.println("  [M] Maintenance");
                            System.out.println();
                            System.out.println("  [X] Return");

                            System.out.println();

                            System.out.print("  > ");

                            char chosenSubOptionId = input.getCharacter();

                            switch (chosenSubOptionId) {
                                case 'S':
                                    System.out.println();

                                    if (!coffeeController.isCapableOfBrewing(chosenTruck)) {
                                        ExceptionMessage.printCustom(
                                                "This coffee truck does not have the ingredients needed to brew coffee.");
                                    }

                                    coffeeController.prepareCoffee(chosenTruck);

                                    break;
                                case 'V':
                                    System.out.println();

                                    truckController.printTruckInfo(chosenTruck);

                                    if (coffeeController.isCapableOfBrewing(chosenTruck)) {
                                        System.out.println();

                                        coffeeController.printPrices(chosenTruck);
                                    }

                                    if (transactionController.hasTransactions(chosenTruck)) {
                                        System.out.println();

                                        transactionController.printTransactions(chosenTruck);
                                    }

                                    break;
                                case 'R':
                                    System.out.println();

                                    truckController.updateStorageBins(chosenTruck);

                                    break;
                                case 'M': {
                                    boolean isRelocating = input
                                            .getBoolean(
                                                    "Do you want to relocate the truck (currently located at: "
                                                            + PrintColor.set(chosenTruck.getLocation(),
                                                                    PrintColor.BRIGHT_CYAN)
                                                            + ") " + PrintColor.set("(true/false)", PrintColor.RED)
                                                            + "?",
                                                    true);

                                    if (isRelocating) {
                                        truckController.relocateTruck(chosenTruck);
                                    }

                                    if (input.getBoolean("Do you want to update the prices (of all trucks) "
                                            + PrintColor.set("(true/false)", PrintColor.RED) + "?", isRelocating)) {
                                        System.out.println();

                                        coffeeController.updatePrices();
                                    }

                                    break;
                                }
                                case 'X':
                                    break;
                                default:
                                    System.out.println();

                                    ExceptionMessage.INVALID_CHARACTER_CHOICE.print();

                                    continue;
                            }

                            break;
                        } catch (InputMismatchException exception) {
                            scanner.nextLine();

                            System.out.println();

                            ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
                        }
                    }

                    break;
                }
                case 'D':
                    System.out.println();

                    truckController.printTrucksInfo();

                    System.out.println();

                    transactionController.printTransactions();

                    break;
                case 'X':
                    isExiting = true;

                    break;
                default:
                    System.out.println();

                    ExceptionMessage.INVALID_CHARACTER_CHOICE.print();

                    break;
            }

            if (!isExiting) {
                System.out.println();
            }
        }

        scanner.close();
    }
}

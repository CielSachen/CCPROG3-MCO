package cielsachen.mco1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.mco1.controller.CoffeeController;
import cielsachen.mco1.controller.TransactionController;
import cielsachen.mco1.controller.TruckController;
import cielsachen.mco1.helper.Input;
import cielsachen.mco1.helper.PrintColor;
import cielsachen.mco1.model.StorageBin;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.model.transaction.Transaction;
import cielsachen.mco1.service.CoffeeService;
import cielsachen.mco1.service.StorageBinService;
import cielsachen.mco1.service.TransactionService;
import cielsachen.mco1.service.TruckService;

public class JavaJeeps {
    public static void main(String[] arguments) {
        boolean isExiting = false;

        Scanner scanner = new Scanner(System.in);
        Input input = Input.getInstance(scanner);

        List<Transaction> transactions = new ArrayList<Transaction>();
        List<StorageBin> storageBins = new ArrayList<StorageBin>();
        List<Truck> trucks = new ArrayList<Truck>();

        TransactionService transactionService = new TransactionService(transactions);
        StorageBinService storageBinService = new StorageBinService(storageBins);
        CoffeeService coffeeService = new CoffeeService(storageBinService);
        TruckService truckService = new TruckService(trucks);

        TransactionController transactionController = new TransactionController(transactionService);
        CoffeeController coffeeController = new CoffeeController(coffeeService, storageBinService, transactionService,
                scanner, input);
        TruckController truckController = new TruckController(truckService, storageBinService, scanner, input);

        while (!isExiting) {
            System.out.println("---------- + ---------- + " + PrintColor.set("JavaJeeps", PrintColor.BRIGHT_YELLOW)
                    + " + ---------- + ----------");

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

                    System.out.println();

                    if (!coffeeController.isPricesSet() || input.getBoolean("Do you want to update the prices "
                            + PrintColor.set("(true/false)", PrintColor.RED) + "?")) {
                        coffeeController.updatePrices();
                    }

                    break;
                }
                case 'S': {
                    if (truckService.getTrucks().size() == 0) {
                        System.out.println();

                        System.out.println(PrintColor.set("You have not yet created any trucks!", PrintColor.RED));

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
                                chosenTruck = truckService.getTruckById(chosenTruckId);

                                break;
                            } else {
                                System.out.println();

                                System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));
                            }
                        } catch (InputMismatchException exception) {
                            if (input.getCharacter() == 'X') {
                                break;
                            }

                            System.out.println();

                            System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));
                        }
                    }

                    if (chosenTruck != null) {
                        while (true) {
                            try {
                                System.out.println();

                                System.out.println("Which feature would you like to simulate?");
                                System.out.println("  [S] Sale and Preparation of the Coffee Drink");
                                System.out.println("  [V] View Truck Information");
                                System.out.println("  [R] Restocking of Storage Bins and Maintenance");
                                System.out.println();
                                System.out.println("  [X] Return");

                                System.out.println();

                                System.out.print("  > ");

                                char chosenSubOptionId = input.getCharacter();

                                switch (chosenSubOptionId) {
                                    case 'S':
                                        System.out.println();

                                        if (!coffeeController.isCapableOfBrewing(chosenTruck)) {
                                            System.out.println(PrintColor.set(
                                                    "This coffee truck does not have the ingredients needed to brew coffee.",
                                                    PrintColor.RED));
                                        }

                                        coffeeController.prepareCoffee(chosenTruck);

                                        break;
                                    case 'V': {
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
                                    }
                                    case 'R':
                                        System.out.println();

                                        System.out.println(PrintColor.set("CURRENTLY UNIMPLEMENTED", PrintColor.RED));

                                        break;
                                    case 'X':
                                        break;
                                    default:
                                        System.out.println();

                                        System.out.println(
                                                PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                                        continue;
                                }

                                break;
                            } catch (InputMismatchException exception) {
                                scanner.nextLine();

                                System.out.println();

                                System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));
                            }
                        }
                    }

                    break;
                }
                case 'D':
                    System.out.println();

                    System.out.println(PrintColor.set("CURRENTLY UNIMPLEMENTED", PrintColor.RED));

                    break;
                case 'X':
                    isExiting = true;

                    break;
                default:
                    System.out.println();

                    System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                    break;
            }

            if (!isExiting) {
                System.out.println();
            }
        }

        scanner.close();
    }
}

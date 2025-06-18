package cielsachen.ccprog3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.controller.CoffeeController;
import cielsachen.ccprog3.controller.TransactionController;
import cielsachen.ccprog3.controller.TruckController;
import cielsachen.ccprog3.helper.Input;
import cielsachen.ccprog3.helper.PrintColor;
import cielsachen.ccprog3.model.StorageBin;
import cielsachen.ccprog3.model.Truck;
import cielsachen.ccprog3.model.transaction.Transaction;
import cielsachen.ccprog3.service.StorageBinService;
import cielsachen.ccprog3.service.TransactionService;
import cielsachen.ccprog3.service.TruckService;

public class JavaJeeps {
    public static void main(String[] arguments) {
        boolean isExiting = false;
        Scanner scanner = new Scanner(System.in);
        Input input = Input.getInstance(scanner);
        List<Truck> trucks = new ArrayList<Truck>();
        TruckService truckService = new TruckService(trucks);
        List<StorageBin> storageBins = new ArrayList<StorageBin>();
        StorageBinService storageBinService = new StorageBinService(storageBins);
        TruckController truckController = new TruckController(truckService, storageBinService, scanner, input);
        CoffeeController coffeeController = new CoffeeController(storageBinService, scanner, input);
        List<Transaction> transactions = new ArrayList<Transaction>();
        TransactionService transactionService = new TransactionService(transactions);
        TransactionController transactionController = new TransactionController(transactionService);

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

            char selectedOptionId = input.getCharacter();

            switch (selectedOptionId) {
                case 'C': {
                    Truck truck = truckController.createTruck();

                    System.out.println();

                    truckController.printTruckInfo(truck);

                    if (!coffeeController.isPricesSet()) {
                        System.out.println();

                        coffeeController.updatePrices();
                    } else {
                        System.out.println();

                        if (input.getBoolean("Do you want to update the prices (true/false)?")) {
                            System.out.println();

                            coffeeController.updatePrices();
                        }
                    }

                    break;
                }
                case 'S': {
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

                            char selectedSubOptionId = input.getCharacter();

                            switch (selectedSubOptionId) {
                                case 'S':
                                    System.out.println();

                                    System.out.println(PrintColor.set("CURRENTLY UNIMPLEMENTED", PrintColor.RED));

                                    break;
                                case 'V': {
                                    if (trucks.size() == 0) {
                                        System.out.println();

                                        System.out.println(
                                                PrintColor.set("You have not yet created any trucks!", PrintColor.RED));

                                        break;
                                    }

                                    while (true) {
                                        try {
                                            System.out.println();

                                            System.out.println("Which truck would you like to view?");

                                            for (int index = 0; index < trucks.size(); index++) {
                                                System.out.println("  [" + index + "] Located at: "
                                                        + trucks.get(index).getLocation());
                                            }

                                            System.out.println();
                                            System.out.println("  [X] Return");

                                            System.out.println();

                                            System.out.print("  > ");

                                            int chosenTruckIndex = scanner.nextInt();

                                            scanner.nextLine();

                                            if (chosenTruckIndex >= 0 && chosenTruckIndex < trucks.size()) {
                                                Truck truck = trucks.get(chosenTruckIndex);

                                                truckController.printTruckInfo(truck);

                                                if (coffeeController.isCapableOfBrewing(truck)) {
                                                    System.out.println();

                                                    coffeeController.printPrices(truck);
                                                }

                                                if (transactionController.hasTransactions(truck)) {
                                                    System.out.println();

                                                    transactionController.printTransactions(truck);
                                                }

                                                break;
                                            } else {
                                                System.out.println();

                                                System.out.println(
                                                        PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));

                                                continue;
                                            }
                                        } catch (InputMismatchException exception) {
                                            scanner.nextLine();

                                            System.out.println();

                                            System.out.println(
                                                    PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));
                                        }
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

                                    System.out
                                            .println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                                    break;
                            }

                            break;
                        } catch (InputMismatchException exception) {
                            scanner.nextLine();

                            System.out.println();

                            System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));
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

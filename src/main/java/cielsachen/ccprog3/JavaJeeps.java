package cielsachen.ccprog3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.controller.TruckController;
import cielsachen.ccprog3.model.coffee.Coffee;
import cielsachen.ccprog3.model.storagebin.StorageBin;
import cielsachen.ccprog3.model.truck.Truck;
import cielsachen.ccprog3.service.CoffeeService;
import cielsachen.ccprog3.service.StorageBinService;
import cielsachen.ccprog3.service.TruckService;
import cielsachen.ccprog3.util.Input;
import cielsachen.ccprog3.util.PrintColor;

public class JavaJeeps {
    public static void main(String[] arguments) {
        boolean isExiting = false;
        Scanner scanner = new Scanner(System.in);
        Input input = Input.getInstance(scanner);
        List<Truck> trucks = new ArrayList<Truck>();
        TruckService truckService = new TruckService(trucks);
        List<StorageBin> storageBins = new ArrayList<StorageBin>();
        StorageBinService storageBinService = new StorageBinService(storageBins);
        List<Coffee> coffees = new ArrayList<Coffee>();
        CoffeeService coffeeService = new CoffeeService(coffees);
        TruckController truckController = new TruckController(truckService, storageBinService, coffeeService, scanner,
                input);

        while (!isExiting) {
            System.out.println("---------- + ---------- + " + PrintColor.setColor("JavaJeeps", PrintColor.BRIGHT_YELLOW)
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

            char selectedOptionId = Character.toUpperCase(scanner.nextLine().charAt(0));

            switch (selectedOptionId) {
                case 'C':
                    truckController.createTruck();

                    break;
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

                            char selectedSubOptionId = Character.toUpperCase(scanner.nextLine().charAt(0));

                            switch (selectedSubOptionId) {
                                case 'S':
                                    break;
                                case 'V': {
                                    if (trucks.size() == 0) {
                                        System.out.println();

                                        System.out.println(PrintColor.setColor("You have not yet created any trucks!",
                                                PrintColor.RED));

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
                                                truckController.printTruckInfo(trucks.get(chosenTruckIndex));

                                                break;
                                            } else {
                                                System.out.println();

                                                System.out.println(PrintColor.setColor(Input.INTEGER_ID_ERROR_MESSAGE,
                                                        PrintColor.RED));

                                                continue;
                                            }
                                        } catch (InputMismatchException exception) {
                                            scanner.nextLine();

                                            System.out.println();

                                            System.out.println(PrintColor.setColor(Input.INTEGER_ID_ERROR_MESSAGE,
                                                    PrintColor.RED));
                                        }
                                    }

                                    break;
                                }
                                case 'R':
                                    break;
                                case 'X':
                                    break;
                                default:
                                    System.out.println();

                                    System.out.println(
                                            PrintColor.setColor(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                                    break;
                            }

                            break;
                        } catch (InputMismatchException exception) {
                            scanner.nextLine();

                            System.out.println();

                            System.out.println(PrintColor.setColor(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));
                        }
                    }

                    break;
                }
                case 'D':
                    System.out.println();

                    System.out
                            .println(PrintColor.setColor("CURRENTLY UNIMPLEMENTED", PrintColor.RED));

                    break;
                case 'X':
                    isExiting = true;

                    break;
                default:
                    System.out.println();

                    System.out.println(PrintColor.setColor(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                    break;
            }

            if (!isExiting) {
                System.out.println();
            }
        }

        scanner.close();
    }
}

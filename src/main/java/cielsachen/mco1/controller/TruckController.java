package cielsachen.mco1.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.mco1.helper.Input;
import cielsachen.mco1.helper.PrintColor;
import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.StorageBin;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.service.StorageBinService;
import cielsachen.mco1.service.TruckService;

public class TruckController {
    private final Input input;
    private final Scanner scanner;

    private final TruckService service;
    private final StorageBinService storageBinService;

    public TruckController(TruckService service, StorageBinService storageBinService, Scanner scanner, Input input) {
        this.input = input;
        this.scanner = scanner;
        this.service = service;
        this.storageBinService = storageBinService;
    }

    public Truck createTruck() {
        System.out.println("----- + ----- + " + PrintColor.set("Creating New Truck", PrintColor.BRIGHT_YELLOW)
                + " + ----- + -----");

        System.out.println();

        String location;

        while (true) {
            System.out.print("Where will this truck be located? ");

            location = this.scanner.nextLine();

            if (this.service.isOccupiedLocation(location)) {
                System.out.println();

                System.out.println(PrintColor.set("A truck already exists on this location!", PrintColor.RED));

                System.out.println();

                continue;
            }

            break;
        }

        int id = this.service.getTrucks().size() + 1;
        boolean isSpecial = this.input.getBoolean(
                "Is this truck a special coffee truck " + PrintColor.set("(true/false)", PrintColor.RED) + "?");

        Truck truck = new Truck(id, location, isSpecial);

        this.service.addTruck(truck);

        System.out.println();

        System.out.println("----- + ----- + " + PrintColor.set("Setting Truck Storage Bins", PrintColor.BRIGHT_YELLOW)
                + " + ----- + -----");

        for (int storageBinId = 1; storageBinId <= (isSpecial ? StorageBin.SPECIAL_TRUCK_AMOUNT
                : StorageBin.STANDARD_TRUCK_AMOUNT); storageBinId++) {
            while (true) {
                try {
                    System.out.println();

                    List<StorageBin> storageBins = this.storageBinService.getStorageBinsByTruck(truck);

                    for (int index = 0; index < storageBins.size(); index++) {
                        System.out.println(
                                PrintColor.set("Bin #" + (index + 1) + " = " + storageBins.get(index).ingredient.name,
                                        PrintColor.BRIGHT_GREEN));
                    }

                    System.out.println("Bin #" + storageBinId + " =");

                    System.out.println();

                    System.out.println("What item should this storage bin contain?");

                    if (storageBinId <= StorageBin.STANDARD_TRUCK_AMOUNT) {
                        System.out.println("  [1] " + Ingredient.SMALL_CUP.name + "s");
                        System.out.println("  [2] " + Ingredient.MEDIUM_CUP.name + "s");
                        System.out.println("  [3] " + Ingredient.LARGE_CUP.name + "s");
                        System.out.println("  [4] " + Ingredient.COFFEE_BEANS.name);
                        System.out.println("  [5] " + Ingredient.MILK.name);
                        System.out.println("  [6] " + Ingredient.WATER.name);
                    } else {
                        System.out.println("  [1] " + Ingredient.HAZELNUT_SYRUP.name);
                        System.out.println("  [2] " + Ingredient.CHOCOLATE_SYRUP.name);
                        System.out.println("  [3] " + Ingredient.ALMOND_SYRUP.name);
                        System.out.println("  [4] " + Ingredient.SWEETENER.name);
                    }

                    System.out.println();

                    System.out.print("  > ");

                    int chosenItemId = this.scanner.nextInt();

                    this.scanner.nextLine();

                    Ingredient ingredient;

                    if (storageBinId <= StorageBin.STANDARD_TRUCK_AMOUNT) {
                        switch (chosenItemId) {
                            case 1:
                                ingredient = Ingredient.SMALL_CUP;

                                break;
                            case 2:
                                ingredient = Ingredient.MEDIUM_CUP;

                                break;
                            case 3:
                                ingredient = Ingredient.LARGE_CUP;

                                break;
                            case 4:
                                ingredient = Ingredient.COFFEE_BEANS;

                                break;
                            case 5:
                                ingredient = Ingredient.MILK;

                                break;
                            case 6:
                                ingredient = Ingredient.WATER;

                                break;
                            default:
                                System.out.println();

                                System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));

                                continue;
                        }
                    } else {
                        switch (chosenItemId) {
                            case 1:
                                ingredient = Ingredient.HAZELNUT_SYRUP;

                                break;
                            case 2:
                                ingredient = Ingredient.CHOCOLATE_SYRUP;

                                break;
                            case 3:
                                ingredient = Ingredient.ALMOND_SYRUP;

                                break;
                            case 4:
                                ingredient = Ingredient.SWEETENER;

                                break;
                            default:
                                System.out.println();

                                System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));

                                continue;
                        }
                    }

                    this.storageBinService.addStorageBin(new StorageBin(storageBinId, truck, ingredient));

                    break;
                } catch (InputMismatchException exception) {
                    this.scanner.nextLine();

                    System.out.println();

                    System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));
                }
            }
        }

        System.out.println();

        System.out.println(PrintColor.set("Created a new coffee truck!", PrintColor.BRIGHT_GREEN));

        return truck;
    }

    public void printTruckInfo(Truck truck) {
        System.out.println("---------- + "
                + PrintColor.set("Truck " + truck.id + " Info Summary", PrintColor.BRIGHT_YELLOW) + " + ----------");

        System.out.println();

        System.out.println("Special Truck: " + (truck.isSpecial ? PrintColor.set("Yes", PrintColor.BRIGHT_GREEN)
                : PrintColor.set("No", PrintColor.RED)));
        System.out.println("Location: " + PrintColor.set(truck.getLocation(), PrintColor.BRIGHT_CYAN));

        System.out.println();

        System.out.println("Storage Bins:");

        List<StorageBin> storageBins = this.storageBinService.getStorageBinsByTruck(truck);

        for (int index = 0; index < storageBins.size(); index++) {
            StorageBin storageBin = storageBins.get(index);

            System.out.println("  Bin " + index + " = "
                    + PrintColor.set(storageBin.ingredient.name, PrintColor.BRIGHT_CYAN) + " ("
                    + PrintColor.set(
                            storageBin.toCapacityString() + " / " + storageBin.ingredient.toMaximumCapacityString(),
                            storageBin.isCriticalCapacity() ? PrintColor.RED : PrintColor.BRIGHT_GREEN)
                    + ")");
        }
    }
}

package cielsachen.mco1.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.mco1.helper.ExceptionMessage;
import cielsachen.mco1.helper.Input;
import cielsachen.mco1.helper.Output;
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
        Output.printHeader2("Create a New Truck");

        String location;

        while (true) {
            System.out.println();

            System.out.print("Where will this truck be located? ");

            location = this.scanner.nextLine();

            if (this.service.isOccupiedLocation(location)) {
                System.out.println();

                ExceptionMessage.printCustom("A truck already exists on this location!");

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

        Output.printHeader2("Set Storage Bin Ingredients");

        List<Ingredient> regularIngredients = Ingredient.regularValues();
        List<Ingredient> specialIngredients = Ingredient.specialValues();

        for (int storageBinId = 1; storageBinId <= (isSpecial ? StorageBin.SPECIAL_TRUCK_COUNT
                : StorageBin.STANDARD_TRUCK_COUNT); storageBinId++) {
            while (true) {
                try {
                    System.out.println();

                    for (StorageBin storageBin : this.storageBinService.getStorageBinsByTruck(truck)) {
                        System.out.println(PrintColor.set("Bin #" + storageBin.id + " = " + storageBin.ingredient.name,
                                PrintColor.BRIGHT_GREEN));
                    }

                    System.out.println("Bin #" + storageBinId + " =");

                    System.out.println();

                    System.out.println("What item should this storage bin contain?");

                    List<Ingredient> ingredients = storageBinId <= StorageBin.STANDARD_TRUCK_COUNT ? regularIngredients
                            : specialIngredients;
                    int ingredientCount = ingredients.size();

                    for (int index = 0; index < ingredientCount; index++) {
                        System.out.println("  [" + (index + 1) + "] " + ingredients.get(index).name);
                    }

                    System.out.println();

                    System.out.print("  > ");

                    int chosenIngredientIndex = this.scanner.nextInt() - 1;

                    this.scanner.nextLine();

                    if (chosenIngredientIndex >= 0 && chosenIngredientIndex < ingredientCount) {
                        this.storageBinService.addStorageBin(new StorageBin(storageBinId, truck,
                                ingredients.get(chosenIngredientIndex)));

                        break;
                    }

                    System.out.println();

                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                } catch (InputMismatchException exception) {
                    this.scanner.nextLine();

                    System.out.println();

                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                }
            }
        }

        System.out.println();

        System.out.println(PrintColor.set("Created a new coffee truck!", PrintColor.BRIGHT_GREEN));

        return truck;
    }

    public void printTruckInfo(Truck truck) {
        Output.printHeader1("Truck " + truck.id + " Info Summary");

        System.out.println();

        System.out.println("Special Truck: " + (truck.isSpecial ? PrintColor.set("Yes", PrintColor.BRIGHT_GREEN)
                : PrintColor.set("No", PrintColor.RED)));
        System.out.println("Location: " + PrintColor.set(truck.getLocation(), PrintColor.BRIGHT_CYAN));

        System.out.println();

        System.out.println("Storage Bins:");

        for (StorageBin storageBin : this.storageBinService.getStorageBinsByTruck(truck)) {
            System.out
                    .println(
                            "  Bin " + storageBin.id + " = "
                                    + PrintColor.set(storageBin.ingredient.name, PrintColor.BRIGHT_CYAN) + " ("
                                    + PrintColor.set(storageBin.toCapacityString(),
                                            storageBin.isCriticalCapacity() ? PrintColor.RED : PrintColor.BRIGHT_GREEN)
                                    + ")");
        }
    }
}

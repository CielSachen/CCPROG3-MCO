package cielsachen.ccprog3.mco1.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.ccprog3.mco1.helper.ExceptionMessage;
import cielsachen.ccprog3.mco1.helper.Input;
import cielsachen.ccprog3.mco1.helper.Output;
import cielsachen.ccprog3.mco1.helper.PrintColor;
import cielsachen.ccprog3.mco1.model.Ingredient;
import cielsachen.ccprog3.mco1.model.StorageBin;
import cielsachen.ccprog3.mco1.model.Truck;
import cielsachen.ccprog3.mco1.service.StorageBinService;
import cielsachen.ccprog3.mco1.service.TruckService;

/** Represents a controller for interacting with trucks. */
public class TruckController {
    private final Input input;
    private final Scanner scanner;

    private final TruckService service;
    private final StorageBinService storageBinService;

    /**
     * Creates a new {@code TruckController} object instance.
     * 
     * @param service           The truck service to use.
     * @param storageBinService The storage bin service to use.
     * @param scanner           The console input scanner to use.
     * @param input             The CLI input helper to use.
     */
    public TruckController(TruckService service, StorageBinService storageBinService, Scanner scanner, Input input) {
        this.input = input;
        this.scanner = scanner;
        this.service = service;
        this.storageBinService = storageBinService;
    }

    /**
     * Creates a new truck.
     * 
     * @return A new truck.
     */
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

        var truck = new Truck(id, location, isSpecial);

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
                        System.out.println(
                                PrintColor.set("Bin #" + storageBin.id + " = " + storageBin.getIngredient().name,
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

        System.out.println();

        System.out.println(PrintColor.set("Deployed the coffee truck to" + location + "!", PrintColor.BRIGHT_GREEN));

        return truck;
    }

    /**
     * Moves the truck to a new unoccupied location.
     * 
     * @param truck The truck to relocate.
     */
    public void relocateTruck(Truck truck) {
        String newLocation;

        while (true) {
            System.out.print("Where will this truck be relocated to? ");

            newLocation = this.scanner.nextLine();

            System.out.println();

            if (this.service.isOccupiedLocation(newLocation)) {
                ExceptionMessage.printCustom("A truck already exists on this location!");

                System.out.println();

                continue;
            }

            break;
        }

        truck.setLocation(newLocation);

        System.out
                .println(PrintColor.set("Relocated the coffee truck to " + newLocation + "!", PrintColor.BRIGHT_GREEN));
    }

    /**
     * Restocks, empties, or changes the ingredient of a truck's storage bin.
     * 
     * @param truck The truck to update a storage bin of.
     */
    public void updateStorageBins(Truck truck) {
        while (true) {
            Output.printHeader2("Update Storage Bins");

            List<StorageBin> storageBins = this.storageBinService.getStorageBinsByTruck(truck);

            StorageBin chosenStorageBin = null;

            while (true) {
                try {
                    System.out.println();

                    System.out.println("Which storage bin would you like to update?");

                    for (StorageBin storageBin : storageBins) {
                        System.out
                                .println("  [" + storageBin.id + "] " + storageBin.getIngredient().name + " ("
                                        + PrintColor.set(storageBin.toCapacityString(),
                                                storageBin.isCriticalCapacity() ? PrintColor.RED
                                                        : PrintColor.BRIGHT_GREEN)
                                        + ")");
                    }

                    System.out.println();
                    System.out.println("  [X] Return");

                    System.out.println();

                    System.out.print("  > ");

                    int chosenStorageBinId = this.scanner.nextInt();

                    this.scanner.nextLine();

                    if (chosenStorageBinId >= 0 && chosenStorageBinId < storageBins.size()) {
                        chosenStorageBin = this.storageBinService.getStorageBinsById(chosenStorageBinId, truck).get();

                        break;
                    }

                    System.out.println();

                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                } catch (InputMismatchException exception) {
                    if (this.input.getCharacter() == 'X') {
                        break;
                    }

                    System.out.println();

                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                }
            }

            if (chosenStorageBin == null) {
                return;
            }

            System.out.println();

            Output.printHeader2("Update Bin " + chosenStorageBin.id);

            double currentCapacity = chosenStorageBin.getCapacity();
            Ingredient currentIngredient = chosenStorageBin.getIngredient();

            while (true) {
                try {
                    System.out.println();

                    System.out.println("What would you like to do to the storage bin?");

                    if (currentCapacity < currentIngredient.maximumCapacity) {
                        System.out.println("  [R] Restock (" + PrintColor.set("+(1+)", PrintColor.BRIGHT_GREEN) + ")");
                    }

                    if (currentCapacity > 0) {
                        System.out.println("  [E] Empty ("
                                + PrintColor.set(String.format("- %.2f", currentCapacity), PrintColor.RED) + ")");
                    }

                    System.out.println("  [C] Change Ingredients");
                    System.out.println();
                    System.out.println("  [X] Return");

                    System.out.println();

                    System.out.print("  > ");

                    char chosenOptionId = this.input.getCharacter();

                    switch (chosenOptionId) {
                        case 'R':
                            if (currentCapacity == currentIngredient.maximumCapacity) {
                                ExceptionMessage.printCustom("The storage bin is already full!");

                                break;
                            }

                            Ingredient storageBinIngredient = chosenStorageBin.getIngredient();

                            float additionalCapacity;

                            while (true) {
                                additionalCapacity = this.input.getFloat(
                                        "How much ("
                                                + PrintColor.set(storageBinIngredient.unitMeasure, PrintColor.YELLOW)
                                                + ") " + PrintColor.set(storageBinIngredient.name, PrintColor.YELLOW)
                                                + " should be restocked? ",
                                        true);

                                if (additionalCapacity > 0) {
                                    if (currentCapacity + additionalCapacity > currentIngredient.maximumCapacity) {
                                        chosenStorageBin.decreaseCapacity(currentCapacity);
                                        chosenStorageBin.increaseCapacity(currentIngredient.maximumCapacity);
                                    } else {
                                        chosenStorageBin.increaseCapacity(additionalCapacity);
                                    }

                                    break;
                                }

                                System.out.println();

                                ExceptionMessage.printCustom("Please only input a positive floating point number!");
                            }

                            System.out.println(
                                    PrintColor.set("The storage bin has been restocked!", PrintColor.BRIGHT_GREEN));

                            break;
                        case 'E':
                            if (currentCapacity == 0) {
                                ExceptionMessage.printCustom("The storage bin is already empty!");

                                break;
                            }

                            chosenStorageBin.decreaseCapacity(currentCapacity);

                            System.out
                                    .println(PrintColor.set("The storage bin has been emptied!",
                                            PrintColor.BRIGHT_GREEN));

                            break;
                        case 'C': {
                            List<Ingredient> ingredients = chosenStorageBin.id <= StorageBin.STANDARD_TRUCK_COUNT
                                    ? Ingredient.regularValues()
                                    : Ingredient.specialValues();
                            int ingredientCount = ingredients.size();

                            while (true) {
                                try {
                                    System.out.println();

                                    for (StorageBin storageBin : this.storageBinService.getStorageBinsByTruck(truck)) {
                                        if (!storageBin.equals(chosenStorageBin)) {
                                            System.out.println(PrintColor.set(
                                                    "Bin #" + storageBin.id + " = " + storageBin.getIngredient().name,
                                                    PrintColor.BRIGHT_CYAN));
                                        }
                                    }

                                    System.out.println();

                                    System.out.println(
                                            "Bin #" + chosenStorageBin.id + " = " + currentIngredient.name);

                                    System.out.println();

                                    System.out.println("What item should this storage bin contain instead?");

                                    for (int index = 0; index < ingredientCount; index++) {
                                        Ingredient ingredient = ingredients.get(index);

                                        if (!ingredient.equals(currentIngredient)) {
                                            System.out.println("  [" + (index + 1) + "] " + ingredient.name);
                                        }
                                    }

                                    System.out.println();

                                    System.out.print("  > ");

                                    int chosenIngredientIndex = this.scanner.nextInt() - 1;

                                    this.scanner.nextLine();

                                    if (chosenIngredientIndex >= 0 && chosenIngredientIndex < ingredientCount) {
                                        Ingredient chosenIngredient = ingredients.get(chosenIngredientIndex);

                                        if (!chosenIngredient.equals(currentIngredient)) {
                                            chosenStorageBin.setIngredient(chosenIngredient);

                                            if (currentCapacity > 0) {
                                                chosenStorageBin.decreaseCapacity(currentCapacity);
                                            }

                                            chosenStorageBin.increaseCapacity(chosenIngredient.maximumCapacity);

                                            break;
                                        }
                                    }

                                    System.out.println();

                                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                                } catch (InputMismatchException exception) {
                                    this.scanner.nextLine();

                                    System.out.println();

                                    ExceptionMessage.INVALID_INTEGER_CHOICE.print();
                                }
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

            System.out.println();
        }
    }

    /** Prints the information summary of all deployed trucks. */
    public void printTrucksInfo() {
        Output.printHeader1("All Trucks Info Summary");

        System.out.println();

        System.out.println("Number of Trucks Deployed: "
                + PrintColor.set(Integer.toString(this.service.getTrucks().size()), PrintColor.BRIGHT_GREEN) + " ("
                + PrintColor.set(this.service.getSpecialTrucks().size() + " Special", PrintColor.BRIGHT_YELLOW) + ")");

        System.out.println();

        System.out.println("Ingredients:");

        for (Ingredient ingredient : Ingredient.values()) {
            System.out.println("  " + ingredient.name + ": "
                    + PrintColor.set(String.format("%.2f",
                            this.storageBinService.getStorageBinsByIngredient(ingredient).stream()
                                    .mapToDouble((storageBin) -> storageBin.getCapacity()).sum())
                            + " " + ingredient.unitMeasure, PrintColor.BRIGHT_CYAN));
        }
    }

    /**
     * Prints the information summary of a truck.
     * 
     * @param truck The truck to print get the info from.
     */
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
                                    + PrintColor.set(storageBin.getIngredient().name, PrintColor.BRIGHT_CYAN) + " ("
                                    + PrintColor.set(storageBin.toCapacityString(),
                                            storageBin.isCriticalCapacity() ? PrintColor.RED : PrintColor.BRIGHT_GREEN)
                                    + ")");
        }
    }
}

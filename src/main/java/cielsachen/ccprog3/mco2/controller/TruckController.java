package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import cielsachen.ccprog3.mco2.helper.ExceptionMessage;
import cielsachen.ccprog3.mco2.helper.Input;
import cielsachen.ccprog3.mco2.helper.Output;
import cielsachen.ccprog3.mco2.helper.PrintColor;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TruckService;
import cielsachen.ccprog3.mco2.view.StorageBinAssignmentView;
import cielsachen.ccprog3.mco2.view.TruckCreationView;

/** Represents a controller for interacting with trucks. */
public class TruckController {
    private final Input input;
    private final Scanner scanner;

    private final CoffeeController coffeeController;

    private final TruckService service;
    private final StorageBinService storageBinService;

    /**
     * Creates a new {@code TruckController} object instance.
     *
     * @param coffeeController  The coffee controller to use.
     * @param service           The truck service to use.
     * @param storageBinService The storage bin service to use.
     */
    public TruckController(CoffeeController coffeeController, TruckService service, StorageBinService storageBinService,
            Scanner scanner,
            Input input) {
        this.coffeeController = coffeeController;

        this.service = service;
        this.storageBinService = storageBinService;

        this.scanner = scanner;
        this.input = input;
    }

    /**
     * Creates a new truck.
     *
     * @return A new truck.
     */
    public void createTruck() {
        var creationView = new TruckCreationView();

        creationView.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String givenLocation = creationView.locationField.getText();

                if (TruckController.this.service.isOccupiedLocation(givenLocation)) {
                    JOptionPane.showMessageDialog(null, "A truck already exists on this location!", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }

                boolean isSpecial = creationView.isSpecialCheckBox.isSelected();

                Truck truck = new Truck(
                        TruckController.this.service.getTrucks().size() + 1, givenLocation, isSpecial);

                TruckController.this.service.addTruck(truck);

                var storageBinAssignmentView = new StorageBinAssignmentView(truck, false);

                storageBinAssignmentView.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < storageBinAssignmentView.ingredientComboBoxes.size(); i++) {
                            JComboBox<Ingredient> ingredientComboBox = storageBinAssignmentView.ingredientComboBoxes
                                    .get(i);

                            TruckController.this.storageBinService.addStorageBin(new StorageBin(i + 1, truck,
                                    (Ingredient) ingredientComboBox.getSelectedItem()));
                        }

                        coffeeController.updatePrices();
                    }
                });
            }
        });
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
                } catch (InputMismatchException e) {
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
                                } catch (InputMismatchException e) {
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
                } catch (InputMismatchException e) {
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

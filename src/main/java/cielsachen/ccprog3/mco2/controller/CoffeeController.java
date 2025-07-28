package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import cielsachen.ccprog3.mco2.exception.InsufficientCapacityException;
import cielsachen.ccprog3.mco2.helper.ExceptionMessage;
import cielsachen.ccprog3.mco2.helper.Input;
import cielsachen.ccprog3.mco2.helper.Output;
import cielsachen.ccprog3.mco2.helper.PrintColor;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.view.component.Modal;
import cielsachen.ccprog3.mco2.view.form.PriceConfigurationForm;

/** Represents a controller for interacting with coffees. */
public class CoffeeController {
    private final Input input;
    private final Scanner scanner;

    private final CoffeeService service;
    private final StorageBinService storageBinService;
    private final TransactionService transactionService;

    /**
     * Creates a new {@code CoffeeController} object instance.
     *
     * @param service            The coffee service to use.
     * @param storageBinService  The storage bin service to use.
     * @param transactionService The transaction service to use.
     * @param scanner            The console input scanner to use.
     * @param input              The CLI input helper to use.
     */
    public CoffeeController(CoffeeService service, StorageBinService storageBinService,
            TransactionService transactionService, Scanner scanner, Input input) {
        this.input = input;
        this.service = service;
        this.scanner = scanner;
        this.storageBinService = storageBinService;
        this.transactionService = transactionService;
    }

    /**
     * Checks if the truck can brew any coffee. This wraps the {@link CoffeeService#isCapableOfBrewing
     * isCapableOfBrewing()} method.
     *
     * @param truck The truck to check.
     * @return Whether the truck can brew any coffee.
     */
    public boolean isCapableOfBrewing(Truck truck) {
        return this.service.isCapableOfBrewing(truck);
    }

    /**
     * Checks if the prices of the coffees and add-ons have been set. This wraps {@link CoffeeService#isPricesSet
     * isPricesSet()} the method.
     *
     * @return Whether the prices of the coffees and add-ons are not the default.
     */
    public boolean isPricesSet() {
        return this.service.isPricesSet();
    }

    // TEMPORARILY PASS TRUCK
    /** Changes the prices of all coffees and add-ons. */
    public void updatePrices(JFrame parentFrame, Truck truck) {
        Coffee[] coffees = this.service.getCoffees();

        var priceConfigurationView = new PriceConfigurationForm(parentFrame, coffees);

        priceConfigurationView.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Float> prices;

                try {
                    prices = priceConfigurationView.priceFields.stream()
                            .map((field) -> Float.parseFloat(field.getText())).toList();
                } catch (NumberFormatException e) {
                    Modal.showError("All fields must be filled!", "Missing Fields");

                    return;
                }

                int i;

                for (i = 0; i < coffees.length; i++) {
                    coffees[i].setPrice(prices.get(i));
                }

                CoffeeController.this.service.espresso.setPrice(prices.get(i));
                CoffeeController.this.service.syrup.setPrice(prices.get(i + 1));

                CoffeeController.this.printPrices(truck);
            }
        });
    }

    /**
     * Prepares a coffee in a truck. This will decrease the capacities of relevant storage bins and create a new
     * transaction.
     *
     * @param truck The truck to prepare the coffee in.
     */
    public void prepareCoffee(Truck truck) {
        Output.printHeader2("Order a Coffee");

        List<Coffee> coffees = this.service.getCoffeesByTruck(truck);
        int coffeeCount = coffees.size();

        Coffee chosenCoffee = null;

        while (true) {
            try {
                System.out.println();

                System.out.println("Which coffee would you like to order?");

                for (int index = 0; index < coffeeCount; index++) {
                    Coffee coffee = coffees.get(index);

                    System.out.println("  [" + (index + 1) + "] " + coffee.name);
                }

                System.out.println();
                System.out.println("  [X] Return");

                System.out.println();

                System.out.print("  > ");

                int chosenCoffeeIndex = this.scanner.nextInt() - 1;

                this.scanner.nextLine();

                if (chosenCoffeeIndex >= 0 && chosenCoffeeIndex < coffeeCount) {
                    chosenCoffee = coffees.get(chosenCoffeeIndex);

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

        if (chosenCoffee == null) {
            return;
        }

        System.out.println();

        CoffeeSize[] coffeeSizes = CoffeeSize.values();

        CoffeeSize chosenSize = null;

        while (true) {
            try {
                System.out.println();

                System.out.println("What size of " + chosenCoffee.name + " would you like?");

                for (int index = 0; index < coffeeSizes.length; index++) {
                    CoffeeSize size = coffeeSizes[index];

                    System.out.println("  [" + (index + 1) + "] " + size.name + " ("
                            + PrintColor.set(size.toString(), PrintColor.BRIGHT_CYAN) + ")");
                }

                System.out.println();

                System.out.print("  > ");

                int chosenSizeIndex = this.scanner.nextInt() - 1;

                this.scanner.nextLine();

                if (chosenSizeIndex >= 0 && chosenSizeIndex < coffeeSizes.length) {
                    chosenSize = coffeeSizes[chosenSizeIndex];

                    break;
                }

                System.out.println();

                ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
            } catch (InputMismatchException e) {
                this.scanner.nextLine();

                System.out.println();

                ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
            }
        }

        EspressoRatio[] espressoRatios = truck.isSpecial ? EspressoRatio.values() : EspressoRatio.regularValues();

        EspressoRatio chosenRatio = EspressoRatio.STANDARD;

        if (truck.isSpecial) {
            while (true) {
                try {
                    System.out.println();

                    System.out.println("What ratio of espresso would you like?");

                    for (int index = 0; index < espressoRatios.length; index++) {
                        EspressoRatio ratio = espressoRatios[index];

                        System.out.println("  [" + (index + 1) + "] " + ratio.name + " ("
                                + PrintColor.set(ratio.toString(), PrintColor.BRIGHT_CYAN) + ")");
                    }

                    System.out.println();

                    System.out.print("  > ");

                    int chosenRatioIndex = this.scanner.nextInt() - 1;

                    this.scanner.nextLine();

                    if (chosenRatioIndex >= 0 && chosenRatioIndex < espressoRatios.length) {
                        chosenRatio = espressoRatios[chosenRatioIndex];

                        break;
                    }

                    System.out.println();

                    ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
                } catch (InputMismatchException e) {
                    this.scanner.nextLine();

                    System.out.println();

                    ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
                }
            }
        }

        if (chosenRatio.equals(EspressoRatio.CUSTOM)) {
            EspressoRatio.setCustomRatio(this.input.getInteger("What should the ratio of water to coffea beans be "
                    + PrintColor.set("(1 : ?)", PrintColor.YELLOW) + "?", true));
        }

        try {
            this.service.canBrewCoffee(truck, chosenCoffee, chosenSize, chosenRatio);

            Map<Ingredient, Double> ingredients = new LinkedHashMap<Ingredient, Double>(
                    this.service.brewCoffee(truck, chosenCoffee, chosenSize, chosenRatio));

            int extraEspressoShotsCount = 0;
            float additionalCost = 0;

            if (truck.isSpecial) {
                boolean isAddingEspressoShots = this.input.getBoolean(
                        "Would you like to add extra shots of espresso "
                                + PrintColor.set("(true/false)", PrintColor.RED) + "?",
                        !chosenRatio.equals(EspressoRatio.CUSTOM));

                if (isAddingEspressoShots) {
                    EspressoRatio chosenShotsRatio = EspressoRatio.STANDARD;

                    if (truck.isSpecial) {
                        while (true) {
                            try {
                                System.out.println();

                                System.out.println("What ratio of espresso would you like?");

                                for (int index = 0; index < espressoRatios.length; index++) {
                                    EspressoRatio ratio = espressoRatios[index];

                                    System.out.println("  [" + (index + 1) + "] " + ratio.name + " ("
                                            + PrintColor.set(ratio.toString(), PrintColor.BRIGHT_CYAN) + ")");
                                }

                                System.out.println();

                                System.out.print("  > ");

                                int chosenRatioIndex = this.scanner.nextInt() - 1;

                                this.scanner.nextLine();

                                if (chosenRatioIndex >= 0 && chosenRatioIndex < espressoRatios.length) {
                                    chosenShotsRatio = espressoRatios[chosenRatioIndex];

                                    break;
                                }

                                System.out.println();

                                ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
                            } catch (InputMismatchException e) {
                                this.scanner.nextLine();

                                System.out.println();

                                ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
                            }
                        }
                    }

                    if (chosenShotsRatio.equals(EspressoRatio.CUSTOM)) {
                        EspressoRatio.setCustomRatio(
                                this.input.getInteger("What should the ratio of water to coffea beans be "
                                        + PrintColor.set("(1 : ?)", PrintColor.YELLOW) + "?", true));
                    }

                    extraEspressoShotsCount = this.input.getInteger("How many extra shots should be added?");

                    try {
                        this.service.canBrewEspressoShots(truck, extraEspressoShotsCount, chosenShotsRatio);

                        ingredients.putAll(this.service.brewEspressoShots(truck, extraEspressoShotsCount,
                                chosenShotsRatio));

                        additionalCost += this.service.espresso.getPrice() * extraEspressoShotsCount;
                    } catch (InsufficientCapacityException e) {
                        System.out.println();

                        System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
                                + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
                                + PrintColor.set("to brew the coffee.", PrintColor.RED));
                    }
                }

                boolean isAddingSyrups = true;

                List<Ingredient> syrups = Ingredient.specialValues();
                int syrupCount = syrups.size();

                while (isAddingSyrups) {
                    isAddingSyrups = this.input.getBoolean("Would you like to add pumps of syrup "
                            + PrintColor.set("(true/false)", PrintColor.RED) + "?");

                    if (isAddingSyrups) {
                        Ingredient chosenSyrup = null;

                        while (true) {
                            try {
                                System.out.println();

                                System.out.println("Which syrup would you like to add?");

                                for (int index = 0; index < syrupCount; index++) {
                                    Ingredient ingredient = syrups.get(index);

                                    if (this.storageBinService.truckHasIngredient(truck, ingredient)) {
                                        System.out.println("  [" + (index + 1) + "] " + ingredient.name);
                                    }
                                }

                                System.out.println();

                                System.out.print("  > ");

                                int chosenSyrupIndex = this.scanner.nextInt() - 1;

                                this.scanner.nextLine();

                                if (chosenSyrupIndex >= 0 && chosenSyrupIndex < syrupCount) {
                                    chosenSyrup = syrups.get(chosenSyrupIndex);

                                    if (this.storageBinService.truckHasIngredient(truck, chosenSyrup)) {
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

                        int amount = this.input.getInteger("How many pumps should be added?", true);

                        try {
                            this.service.canAddSyrup(truck, chosenSyrup, amount);

                            ingredients.putAll(this.service.addSyrup(truck, chosenSyrup, amount));

                            additionalCost += this.service.syrup.getPrice() * amount;
                        } catch (InsufficientCapacityException e) {
                            System.out.println();

                            System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
                                    + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
                                    + PrintColor.set("to brew the coffee.", PrintColor.RED));
                        }
                    }
                }
            }

            System.out.println();

            System.out.println(">>> Preparing a " + PrintColor.set(chosenSize.name, PrintColor.YELLOW) + " of "
                    + PrintColor.set(chosenCoffee.name, PrintColor.YELLOW) + " ("
                    + PrintColor.set(chosenSize.toString(), PrintColor.BRIGHT_CYAN) + ") with "
                    + PrintColor.set(Integer.toString(extraEspressoShotsCount), PrintColor.BRIGHT_CYAN)
                    + PrintColor.set(" extra shots of espresso...", PrintColor.YELLOW));
            System.out.println(">>> Brewing " + PrintColor.set(chosenRatio.name + " Espresso", PrintColor.YELLOW));

            for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
                Ingredient ingredient = entry.getKey();

                System.out.println(">>> Adding " + PrintColor.set(ingredient.name, PrintColor.YELLOW) + " ("
                        + PrintColor.set(String.format("%.2f", entry.getValue()) + " " + ingredient.unitMeasure,
                                PrintColor.BRIGHT_CYAN)
                        + ")...");
            }

            System.out.println(">>> The " + chosenCoffee.name + " is done!");

            System.out.println();

            float totalCost = chosenCoffee.getPrice() + additionalCost;

            System.out.println("The " + PrintColor.set(chosenSize.name, PrintColor.YELLOW) + " of "
                    + PrintColor.set(chosenCoffee.name, PrintColor.YELLOW) + " will cost "
                    + PrintColor.set(totalCost + " PHP", PrintColor.BRIGHT_GREEN) + ".");

            this.transactionService.addTransaction(new Transaction(chosenCoffee.name, chosenSize, totalCost, truck,
                    extraEspressoShotsCount, ingredients));
        } catch (InsufficientCapacityException e) {
            System.out.println();

            System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
                    + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
                    + PrintColor.set("to brew the coffee.", PrintColor.RED));
        }
    }

    /**
     * Prints the prices of all coffees and add-ons.
     *
     * @param truck The truck to print the prices of.
     */
    public void printPrices(Truck truck) {
        Output.printHeader3("Coffee and Add-On Prices");

        System.out.println();

        System.out.println("Coffees:");

        for (Coffee coffee : this.service.getCoffeesByTruck(truck)) {
            System.out.println(
                    "  " + coffee.name + " = " + PrintColor.set(coffee.toPriceString(), PrintColor.BRIGHT_GREEN));
        }

        if (truck.isSpecial) {
            System.out.println();

            System.out.println("Add-Ons:");

            if (this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER)) {
                System.out.println("  Extra Espresso Shots = "
                        + PrintColor.set(this.service.espresso.toPriceString(), PrintColor.BRIGHT_GREEN));
            }

            if (this.storageBinService.truckHasSyrups(truck)) {
                System.out.println("  Syrup Add-Ons = "
                        + PrintColor.set(this.service.syrup.toPriceString(), PrintColor.BRIGHT_GREEN));
            }
        }
    }
}

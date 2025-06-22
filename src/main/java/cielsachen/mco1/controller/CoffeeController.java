package cielsachen.mco1.controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cielsachen.mco1.helper.Input;
import cielsachen.mco1.helper.PrintColor;
import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.model.coffee.Coffee;
import cielsachen.mco1.model.coffee.CoffeeSize;
import cielsachen.mco1.model.coffee.EspressoRatio;
import cielsachen.mco1.model.transaction.Transaction;
import cielsachen.mco1.model.transaction.TransactionIngredient;
import cielsachen.mco1.service.CoffeeService;
import cielsachen.mco1.service.StorageBinService;
import cielsachen.mco1.service.TransactionService;

public class CoffeeController {
    private final Input input;
    private final Scanner scanner;

    private final CoffeeService service;
    private final StorageBinService storageBinService;
    private final TransactionService transactionService;

    public CoffeeController(CoffeeService service, StorageBinService storageBinService,
            TransactionService transactionService, Scanner scanner, Input input) {
        this.input = input;
        this.service = service;
        this.scanner = scanner;
        this.storageBinService = storageBinService;
        this.transactionService = transactionService;
    }

    public boolean isCapableOfBrewing(Truck truck) {
        return this.service.isCapableOfBrewing(truck);
    }

    public boolean isPricesSet() {
        return this.service.isPricesSet();
    }

    public void updatePrices() {
        System.out.println("----- + ----- + " + PrintColor.set("Changing Coffee Prices", PrintColor.BRIGHT_YELLOW)
                + " + ----- + -----");

        System.out.println();

        for (Coffee coffee : this.service.getCoffees()) {
            coffee.setPrice(this.input.getFloat("What base price should "
                    + PrintColor.set(coffee.name + "s", PrintColor.YELLOW) + " should have?"));
        }

        System.out.println();

        this.service.espresso.setPrice(this.input.getFloat(
                "What price should " + PrintColor.set("extra espresso shots", PrintColor.YELLOW) + " should have?"));
        this.service.syrup.setPrice(this.input.getFloat(
                "What base price should " + PrintColor.set("syrup add-ons", PrintColor.YELLOW) + " should have?"));
    }

    public void prepareCoffee(Truck truck) {
        System.out.println(
                "----- + ----- + " + PrintColor.set("Order a Coffee", PrintColor.BRIGHT_YELLOW) + " + ----- + -----");

        List<Coffee> coffees = this.service.getCoffeesByTruck(truck);
        Coffee chosenCoffee = null;

        while (true) {
            try {
                System.out.println();

                System.out.println("Which coffee would you like to order?");

                for (int index = 0; index < coffees.size(); index++) {
                    Coffee coffee = coffees.get(index);

                    System.out.println("  [" + (index + 1) + "] " + coffee.name);
                }

                System.out.println();
                System.out.println("  [X] Return");

                System.out.println();

                System.out.print("  > ");

                int chosenCoffeeIndex = scanner.nextInt() - 1;

                scanner.nextLine();

                if (chosenCoffeeIndex >= 0 && chosenCoffeeIndex < coffees.size()) {
                    chosenCoffee = coffees.get(chosenCoffeeIndex);

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

        if (chosenCoffee == null) {
            return;
        }

        System.out.println();

        CoffeeSize size = null;

        while (true) {
            try {
                System.out.println();

                System.out.println("What size of " + chosenCoffee.name + " would you like?");
                System.out.println("  [S] Small ("
                        + PrintColor.set(CoffeeSize.SMALL_CUP.toString(), PrintColor.BRIGHT_GREEN) + ")");
                System.out.println("  [M] Medium ("
                        + PrintColor.set(CoffeeSize.MEDIUM_CUP.toString(), PrintColor.BRIGHT_GREEN) + ")");
                System.out.println("  [L] Large ("
                        + PrintColor.set(CoffeeSize.LARGE_CUP.toString(), PrintColor.BRIGHT_GREEN) + ")");
                System.out.println();
                System.out.println("  [X] Return");

                System.out.println();

                System.out.print("  > ");

                char chosenSize = input.getCharacter();

                switch (chosenSize) {
                    case 'S':
                        size = CoffeeSize.SMALL_CUP;

                        break;
                    case 'M':
                        size = CoffeeSize.MEDIUM_CUP;

                        break;
                    case 'L':
                        size = CoffeeSize.LARGE_CUP;

                        break;
                    default:
                        System.out.println();

                        System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                        continue;
                }

                break;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));
            }
        }

        EspressoRatio ratio = EspressoRatio.STANDARD;

        if (truck.isSpecial) {
            while (true) {
                try {
                    System.out.println();

                    System.out.println("What ratio of espresso would you like?");
                    System.out.println("  [L] Light ("
                            + PrintColor.set(EspressoRatio.LIGHT.toString(), PrintColor.BRIGHT_GREEN) + ")");
                    System.out.println("  [S] Standard ("
                            + PrintColor.set(EspressoRatio.STANDARD.toString(), PrintColor.BRIGHT_GREEN) + ")");
                    System.out.println("  [R] Strong ("
                            + PrintColor.set(EspressoRatio.STRONG.toString(), PrintColor.BRIGHT_GREEN) + ")");
                    System.out.println();
                    System.out.println("  [X] Return");

                    System.out.println();

                    System.out.print("  > ");

                    char chosenSize = input.getCharacter();

                    switch (chosenSize) {
                        case 'L':
                            ratio = EspressoRatio.LIGHT;

                            break;
                        case 'S':
                            break;
                        case 'R':
                            ratio = EspressoRatio.STRONG;

                            break;
                        default:
                            System.out.println();

                            System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));

                            continue;
                    }

                    break;
                } catch (InputMismatchException exception) {
                    this.scanner.nextLine();

                    System.out.println();

                    System.out.println(PrintColor.set(Input.CHARACTER_ID_ERROR_MESSAGE, PrintColor.RED));
                }
            }
        }

        try {
            this.service.canBrewCoffee(truck, chosenCoffee, size, ratio);

            List<TransactionIngredient> transactionIngredients = new ArrayList<TransactionIngredient>(
                    this.service.brewCoffee(truck, chosenCoffee, size, ratio));

            int extraEspressoShotsAmount = 0;
            float additionalCost = 0;

            if (truck.isSpecial) {
                System.out.println();

                boolean isAddingEspressoShots = this.input.getBoolean("Would you like to add extra shots of espresso "
                        + PrintColor.set("(true/false)", PrintColor.RED) + "?");

                if (isAddingEspressoShots) {
                    extraEspressoShotsAmount = this.input.getInteger("How many extra shots should be added?");

                    try {
                        this.service.canBrewEspressoShots(truck, extraEspressoShotsAmount, ratio);

                        transactionIngredients.addAll(this.service.brewEspressoShots(truck,
                                extraEspressoShotsAmount, ratio));

                        additionalCost += this.service.espresso.getPrice() * extraEspressoShotsAmount;

                        System.out.println();
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }

                boolean isAddingSyrups = true;

                while (isAddingSyrups) {
                    isAddingSyrups = this.input.getBoolean("Would you like to add pumps of syrup "
                            + PrintColor.set("(true/false)", PrintColor.RED) + "?");

                    if (isAddingSyrups) {
                        Ingredient chosenSyrup = null;

                        while (true) {
                            try {
                                System.out.println();

                                System.out.println("Which syrup would you like to add?");

                                if (this.storageBinService.truckHasIngredient(truck, Ingredient.HAZELNUT_SYRUP)) {
                                    System.out.println("  [1] " + Ingredient.HAZELNUT_SYRUP.name);
                                }

                                if (this.storageBinService.truckHasIngredient(truck, Ingredient.CHOCOLATE_SYRUP)) {
                                    System.out.println("  [2] " + Ingredient.CHOCOLATE_SYRUP.name);
                                }

                                if (this.storageBinService.truckHasIngredient(truck, Ingredient.ALMOND_SYRUP)) {
                                    System.out.println("  [3] " + Ingredient.ALMOND_SYRUP.name);
                                }

                                if (this.storageBinService.truckHasIngredient(truck, Ingredient.SWEETENER)) {
                                    System.out.println("  [4] " + Ingredient.SWEETENER.name);
                                }

                                System.out.println();

                                System.out.print("  > ");

                                int chosenSyrupId = scanner.nextInt();

                                scanner.nextLine();

                                switch (chosenSyrupId) {
                                    case 1:
                                        chosenSyrup = Ingredient.HAZELNUT_SYRUP;

                                        break;
                                    case 2:
                                        chosenSyrup = Ingredient.CHOCOLATE_SYRUP;

                                        break;
                                    case 3:
                                        chosenSyrup = Ingredient.ALMOND_SYRUP;

                                        break;
                                    case 4:
                                        chosenSyrup = Ingredient.SWEETENER;

                                        break;
                                    default:
                                        System.out.println();

                                        System.out.println(
                                                PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));

                                        continue;
                                }

                                break;
                            } catch (InputMismatchException exception) {
                                this.scanner.nextLine();

                                System.out.println();

                                System.out.println(PrintColor.set(Input.INTEGER_ID_ERROR_MESSAGE, PrintColor.RED));
                            }
                        }

                        System.out.println();

                        int amount = this.input.getInteger("How many pumps should be added?");

                        try {
                            this.service.canAddSyrup(truck, chosenSyrup, amount);

                            transactionIngredients.add(this.service.addSyrup(truck, chosenSyrup, amount));

                            additionalCost += this.service.syrup.getPrice() * amount;
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }

                        System.out.println();
                    }
                }
            }

            System.out.println();

            System.out.println(">>> Preparing a " + PrintColor.set(size.name, PrintColor.YELLOW) + " of "
                    + PrintColor.set(chosenCoffee.name, PrintColor.YELLOW) + " ("
                    + PrintColor.set(size.toString(), PrintColor.BRIGHT_GREEN) + ")");
            System.out.println(">>> Brewing " + PrintColor.set(ratio.name + " Espresso", PrintColor.YELLOW));

            for (TransactionIngredient transactionIngredient : transactionIngredients) {
                System.out
                        .println(">>> Adding "
                                + PrintColor.set(transactionIngredient.ingredient.name, PrintColor.BRIGHT_CYAN) + " ("
                                + PrintColor.set(String.format("%.2f", transactionIngredient.getAmount()) + " "
                                        + transactionIngredient.ingredient.unitMeasure, PrintColor.BRIGHT_GREEN)
                                + ")...");
            }

            System.out.println(">>> The " + chosenCoffee.name + " is done!");

            System.out.println();

            float totalCost = chosenCoffee.getPrice() + additionalCost;

            System.out.println("The " + PrintColor.set(size.name, PrintColor.YELLOW) + " of "
                    + PrintColor.set(chosenCoffee.name, PrintColor.YELLOW) + " will cost "
                    + PrintColor.set(totalCost + " PHP", PrintColor.BRIGHT_GREEN) + ".");

            this.transactionService.addTransaction(new Transaction(chosenCoffee.name, size, totalCost, truck,
                    extraEspressoShotsAmount, transactionIngredients));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void printPrices(Truck truck) {
        System.out.println("---------- + " + PrintColor.set("Coffee and Add-On Prices", PrintColor.BRIGHT_YELLOW)
                + " + ----------");

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

            if (this.storageBinService.truckHasSyrupAddOns(truck)) {
                System.out.println("  Syrup Add-Ons = "
                        + PrintColor.set(this.service.syrup.toPriceString(), PrintColor.BRIGHT_GREEN));
            }
        }
    }
}

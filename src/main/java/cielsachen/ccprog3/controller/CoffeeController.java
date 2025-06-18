package cielsachen.ccprog3.controller;

import java.util.Scanner;

import cielsachen.ccprog3.helper.Input;
import cielsachen.ccprog3.helper.PrintColor;
import cielsachen.ccprog3.model.Ingredient;
import cielsachen.ccprog3.model.Truck;
import cielsachen.ccprog3.model.coffee.Americano;
import cielsachen.ccprog3.model.coffee.Cappuccino;
import cielsachen.ccprog3.model.coffee.Coffee;
import cielsachen.ccprog3.model.coffee.Espresso;
import cielsachen.ccprog3.model.coffee.Latte;
import cielsachen.ccprog3.model.coffee.SyrupAddOn;
import cielsachen.ccprog3.service.StorageBinService;

public class CoffeeController {
    private final Americano americano = new Americano(this.espresso);
    private final Cappuccino cappuccino = new Cappuccino(this.espresso);
    private final Espresso espresso = new Espresso();
    private final Input input;
    private final Latte latte = new Latte(this.espresso);
    private final Scanner scanner;
    private final StorageBinService storageBinService;
    private final SyrupAddOn syrupAddOn = new SyrupAddOn();

    public CoffeeController(StorageBinService storageBinService, Scanner scanner, Input input) {
        this.input = input;
        this.scanner = scanner;
        this.storageBinService = storageBinService;
    }

    public boolean isCapableOfBrewing(Truck truck) {
        return this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER);
    }

    public boolean isPricesSet() {
        if (this.espresso.getPrice() == Coffee.DEFAULT_PRICE
                || this.americano.getPrice() == Coffee.DEFAULT_PRICE
                || this.latte.getPrice() == Coffee.DEFAULT_PRICE
                || this.cappuccino.getPrice() == Coffee.DEFAULT_PRICE
                || this.syrupAddOn.getPrice() == Coffee.DEFAULT_PRICE) {
            return false;
        }

        return true;
    }

    public void updatePrices() {
        System.out.println("----- + ----- + " + PrintColor.set("Changing Coffee Prices", PrintColor.BRIGHT_YELLOW)
                + " + ----- + -----");

        System.out.println();

        this.americano.setBasePrice(this.input.getFloat("What base price should "
                + PrintColor.set(this.americano.name + "s", PrintColor.YELLOW) + " should have?"));
        this.latte.setBasePrice(this.input.getFloat("What base price should "
                + PrintColor.set(this.latte.name + "s", PrintColor.YELLOW) + " should have?"));
        this.cappuccino.setBasePrice(this.input.getFloat("What base price should "
                + PrintColor.set(this.cappuccino.name + "s", PrintColor.YELLOW) + " should have?"));

        System.out.println();

        this.espresso.setPrice(this.input.getFloat(
                "What price should " + PrintColor.set("extra espresso shots", PrintColor.YELLOW) + " should have?"));
        this.syrupAddOn.setPrice(this.input.getFloat(
                "What base price should " + PrintColor.set("syrup add-ons", PrintColor.YELLOW) + " should have?"));
    }

    public void printPrices(Truck truck) {
        System.out.println("---------- + " + PrintColor.set("Coffee and Add-On Prices", PrintColor.BRIGHT_YELLOW)
                + " + ----------");

        System.out.println();

        System.out.println("Coffees:");

        if (this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER)) {
            System.out.println("  " + this.americano.name + " = "
                    + PrintColor.set(this.americano.toPriceString(), PrintColor.BRIGHT_GREEN));

            if (this.storageBinService.truckHasIngredient(truck, Ingredient.MILK)) {
                System.out.println("  " + this.latte.name + " = "
                        + PrintColor.set(this.latte.toPriceString(), PrintColor.BRIGHT_GREEN));

                System.out.println("  " + this.cappuccino.name + " = "
                        + PrintColor.set(this.cappuccino.toPriceString(), PrintColor.BRIGHT_GREEN));
            }
        }

        if (truck.isSpecial) {
            System.out.println();

            System.out.println("Add-Ons:");

            if (this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER)) {
                System.out.println("  Extra Espresso Shots = "
                        + PrintColor.set(this.espresso.toPriceString(), PrintColor.BRIGHT_GREEN));
            }

            if (this.storageBinService.truckHasSyrupAddOns(truck)) {
                System.out.println("  Syrup Add-Ons = "
                        + PrintColor.set(this.syrupAddOn.toPriceString(), PrintColor.BRIGHT_GREEN));
            }
        }
    }
}

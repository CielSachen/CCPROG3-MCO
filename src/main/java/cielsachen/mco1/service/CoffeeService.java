package cielsachen.mco1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Product;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.model.coffee.Americano;
import cielsachen.mco1.model.coffee.Cappuccino;
import cielsachen.mco1.model.coffee.Coffee;
import cielsachen.mco1.model.coffee.CoffeeSize;
import cielsachen.mco1.model.coffee.EspressoRatio;
import cielsachen.mco1.model.coffee.Latte;
import cielsachen.mco1.model.transaction.TransactionIngredient;
import cielsachen.mco1.util.UnitConversion;

public class CoffeeService {
    public final Americano americano = new Americano();
    public final Cappuccino cappuccino = new Cappuccino();
    public final Latte latte = new Latte();

    public final Product espresso = new Product();
    public final Product syrup = new Product();

    private final StorageBinService storageBinService;

    public CoffeeService(StorageBinService storageBinService) {
        this.storageBinService = storageBinService;
    }

    public TransactionIngredient addSyrup(Truck truck, Ingredient syrup, int amount) {
        double syrupAmount = 0.5 * amount;

        this.storageBinService.decreaseCapacityByTruck(truck, syrup, syrupAmount);

        return new TransactionIngredient(syrup, amount * syrupAmount);
    }

    public List<TransactionIngredient> brewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) {
        double extraIngredientAmount = coffee.extraIngredientRatio * size.capacity;
        int cupCount = 1;

        List<TransactionIngredient> transactionIngredients = new ArrayList<TransactionIngredient>(
                this.brewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio));

        this.storageBinService.decreaseCapacityByTruck(truck, coffee.extraIngredient, extraIngredientAmount);
        this.storageBinService.decreaseCapacityByTruck(truck, size.cup, cupCount);

        transactionIngredients.add(new TransactionIngredient(coffee.extraIngredient, extraIngredientAmount));
        transactionIngredients.add(new TransactionIngredient(size.cup, cupCount));

        return Collections.unmodifiableList(transactionIngredients);
    }

    public List<TransactionIngredient> brewEspressoShots(Truck truck, double amount, EspressoRatio ratio) {
        double coffeeBeanAmount = UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount;
        double waterAmount = ratio.waterDecimal * amount;

        this.storageBinService.decreaseCapacityByTruck(truck, Ingredient.COFFEE_BEANS, coffeeBeanAmount);
        this.storageBinService.decreaseCapacityByTruck(truck, Ingredient.WATER, waterAmount);

        return List.of(new TransactionIngredient(Ingredient.COFFEE_BEANS, coffeeBeanAmount),
                new TransactionIngredient(Ingredient.WATER, waterAmount));

    }

    public boolean canAddSyrup(Truck truck, Ingredient syrup, int amount) throws Exception {
        if (this.storageBinService.getTotalCapacityByTruck(truck, syrup) < 0.5) {
            throw new Exception("The truck does not have enough syrup!");
        }

        return true;
    }

    public boolean canBrewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) throws Exception {
        this.canBrewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio);

        double extraIngredientTotalCapacity = this.storageBinService.getTotalCapacityByTruck(truck,
                coffee.extraIngredient);

        if (coffee instanceof Americano) {
            if (extraIngredientTotalCapacity < (ratio.waterDecimal * coffee.espressoRatio * size.capacity)
                    + (coffee.extraIngredientRatio * size.capacity)) {
                throw new Exception("The truck does not have enough water!");
            }
        } else if (extraIngredientTotalCapacity < coffee.extraIngredientRatio * size.capacity) {
            throw new Exception("The truck does not have enough milk!");
        } else if (this.storageBinService.getTotalCapacityByTruck(truck, size.cup) == 0) {
            throw new Exception("The truck does not have enough cups!");
        }

        return true;
    }

    public boolean canBrewEspressoShots(Truck truck, double amount, EspressoRatio ratio) throws Exception {
        if (this.storageBinService.getTotalCapacityByTruck(truck,
                Ingredient.COFFEE_BEANS) < UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount) {
            throw new Exception("The truck does not have enough coffee beans!");
        } else if (this.storageBinService.getTotalCapacityByTruck(truck, Ingredient.WATER) < ratio.waterDecimal
                * amount) {
            throw new Exception("The truck does not have enough water!");
        }

        return true;
    }

    public Coffee[] getCoffees() {
        return new Coffee[] { this.americano, this.latte, this.cappuccino };
    }

    public List<Coffee> getCoffeesByTruck(Truck truck) {
        List<Coffee> coffees = new ArrayList<Coffee>();

        for (Coffee coffee : this.getCoffees()) {
            if (this.isCapableOfBrewing(truck, coffee)) {
                coffees.add(coffee);
            }
        }

        return coffees;
    }

    public boolean isCapableOfBrewing(Truck truck) {
        return this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER);
    }

    public boolean isCapableOfBrewing(Truck truck, Coffee coffee) {
        return this.storageBinService.truckHasIngredient(truck, coffee.extraIngredient, Ingredient.COFFEE_BEANS,
                Ingredient.WATER);
    }

    public boolean isPricesSet() {
        if (this.espresso.getPrice() == Coffee.DEFAULT_PRICE
                || this.americano.getPrice() == Coffee.DEFAULT_PRICE
                || this.latte.getPrice() == Coffee.DEFAULT_PRICE
                || this.cappuccino.getPrice() == Coffee.DEFAULT_PRICE
                || this.syrup.getPrice() == Coffee.DEFAULT_PRICE) {
            return false;
        }

        return true;
    }
}

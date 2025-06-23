package cielsachen.mco1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Product;
import cielsachen.mco1.model.StorageBin;
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
        this.storageBinService.getStorageBinByTruck(truck, syrup).get().decreaseCapacity(amount * 0.5);

        return new TransactionIngredient(syrup, amount * 0.5);
    }

    public List<TransactionIngredient> brewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) {
        double extraIngredientAmount = coffee.extraIngredientRatio * size.capacity;

        List<TransactionIngredient> transactionIngredients = new ArrayList<TransactionIngredient>(
                this.brewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio));

        this.storageBinService.getStorageBinByTruck(truck, coffee.extraIngredient).get()
                .decreaseCapacity(extraIngredientAmount);
        this.storageBinService.getStorageBinByTruck(truck, size.cup).get().decreaseCapacity(1);

        transactionIngredients.add(new TransactionIngredient(coffee.extraIngredient, extraIngredientAmount));
        transactionIngredients.add(new TransactionIngredient(size.cup, 1));

        return Collections.unmodifiableList(transactionIngredients);
    }

    public List<TransactionIngredient> brewEspressoShots(Truck truck, double amount, EspressoRatio ratio) {
        double coffeeBeansAmount = UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount;
        double waterAmount = ratio.waterDecimal * amount;

        this.storageBinService.getStorageBinByTruck(truck, Ingredient.COFFEE_BEANS).get()
                .decreaseCapacity(coffeeBeansAmount);
        this.storageBinService.getStorageBinByTruck(truck, Ingredient.WATER).get().decreaseCapacity(waterAmount);

        return List.of(new TransactionIngredient(Ingredient.COFFEE_BEANS, coffeeBeansAmount),
                new TransactionIngredient(Ingredient.WATER, waterAmount));
    }

    public boolean canAddSyrup(Truck truck, Ingredient syrup, int amount) throws Exception {
        Optional<StorageBin> syrupStorageBin = this.storageBinService.getStorageBinByTruck(truck, syrup);

        if (syrupStorageBin.isEmpty() || syrupStorageBin.get().getCapacity() < 0.5) {
            throw new Exception("The truck does not have enough syrup!");
        }

        return true;
    }

    public boolean canBrewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) throws Exception {
        this.canBrewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio);

        Optional<StorageBin> extraIngredientStorageBin = this.storageBinService.getStorageBinByTruck(truck,
                coffee.extraIngredient);
        Optional<StorageBin> cupStorageBin = this.storageBinService.getStorageBinByTruck(truck,
                size.cup);

        if (coffee instanceof Americano) {
            if (extraIngredientStorageBin.isEmpty() || extraIngredientStorageBin.get()
                    .getCapacity() < (ratio.waterDecimal * coffee.espressoRatio * size.capacity)
                            + (coffee.extraIngredientRatio * size.capacity)) {
                throw new Exception("The truck does not have enough water!");
            }
        } else if (extraIngredientStorageBin.get().getCapacity() < coffee.extraIngredientRatio * size.capacity) {
            throw new Exception("The truck does not have enough milk!");
        } else if (cupStorageBin.isEmpty() || cupStorageBin.get().getCapacity() == 0) {
            throw new Exception("The truck does not have enough cups!");
        }

        return true;
    }

    public boolean canBrewEspressoShots(Truck truck, double amount, EspressoRatio ratio) throws Exception {
        Optional<StorageBin> coffeeBeansStorageBin = this.storageBinService.getStorageBinByTruck(truck,
                Ingredient.COFFEE_BEANS);
        Optional<StorageBin> waterStorageBin = this.storageBinService.getStorageBinByTruck(truck, Ingredient.WATER);

        if (coffeeBeansStorageBin.isEmpty() || coffeeBeansStorageBin.get()
                .getCapacity() < UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount) {
            throw new Exception("The truck does not have enough coffee beans!");
        } else if (waterStorageBin.isEmpty() || waterStorageBin.get().getCapacity() < ratio.waterDecimal * amount) {
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

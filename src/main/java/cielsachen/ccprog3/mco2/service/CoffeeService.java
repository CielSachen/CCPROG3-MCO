package cielsachen.ccprog3.mco2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cielsachen.ccprog3.mco2.exception.InsufficientCapacityException;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Product;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.util.UnitConversion;

/**
 * Represents a service for managing coffees. The business logic happens here instead of in the models.
 * <p>
 * This uses a standard Java {@link List list} instead of a dedicated repository.
 */
public class CoffeeService {
    /** The brewable americano coffee. */
    public final Coffee americano = new Coffee("Café Americano", Ingredient.WATER, 1.0 / 3.0, 2.0 / 3.0);
    /** The brewable Cappuccino coffee. */
    public final Coffee cappuccino = new Coffee("Cappuccino", Ingredient.MILK, 1.0 / 3.0, 2.0 / 3.0);
    /** The brewable Latte coffee. */
    public final Coffee latte = new Coffee("Latte", Ingredient.MILK, 1.0 / 5.0, 4.0 / 5.0);

    /** The addable extra espresso shot. */
    public final Product espresso = new Product();
    /** The addable syrup add-on. */
    public final Product syrup = new Product();

    private final StorageBinService storageBinService;

    /**
     * Creates and returns a new {@code CoffeeService} object instance.
     *
     * @param storageBinService The storage bin service to use.
     */
    public CoffeeService(StorageBinService storageBinService) {
        this.storageBinService = storageBinService;
    }

    /**
     * Gets all the brewable coffees.
     *
     * @return The brewable coffees.
     */
    public Coffee[] getCoffees() {
        return new Coffee[] { this.americano, this.latte, this.cappuccino };
    }

    /**
     * Gets all coffees a truck can brew.
     *
     * @param truck The truck that can brew the coffees.
     * @return The coffees the truck can brew.
     */
    public List<Coffee> getCoffeesByTruck(Truck truck) {
        List<Coffee> coffees = new ArrayList<Coffee>();

        for (Coffee coffee : this.getCoffees()) {
            if (this.isCapableOfBrewing(truck, coffee)) {
                coffees.add(coffee);
            }
        }

        return coffees;
    }

    /**
     * Adds syrup to an already brewed coffee. This will decrease the capacities of relevant storage bins.
     *
     * @param truck  The truck to add from.
     * @param syrup  The syrup to add.
     * @param amount The amount of syrup to add.
     * @return The ingredients used mapped to their amounts.
     */
    public Map<Ingredient, Double> addSyrup(Truck truck, Ingredient syrup, int amount) {
        double syrupAmt = 0.5 * amount;

        this.storageBinService.decreaseCapacityByTruck(truck, syrup, syrupAmt);

        return Map.of(syrup, amount * syrupAmt);
    }

    /**
     * Brews a coffee. This will decrease the capacities of relevant storage bins.
     *
     * @param truck  The truck to brew from.
     * @param coffee The coffee to brew.
     * @param size   The size of the coffee.
     * @param ratio  The espresso ratio to use.
     * @return The ingredients used mapped to their amounts.
     */
    public Map<Ingredient, Double> brewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) {
        double extraIngredientAmt = coffee.extraIngredientRatio * size.capacity;
        double cupCnt = 1;

        Map<Ingredient, Double> transactionIngredients = new LinkedHashMap<Ingredient, Double>(
                this.brewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio));

        this.storageBinService.decreaseCapacityByTruck(truck, coffee.extraIngredient, extraIngredientAmt);
        this.storageBinService.decreaseCapacityByTruck(truck, size.cup, cupCnt);

        transactionIngredients.put(coffee.extraIngredient, extraIngredientAmt);
        transactionIngredients.put(size.cup, cupCnt);

        return Collections.unmodifiableMap(transactionIngredients);
    }

    /**
     * Brews espresso shots. This will decrease the capacities of relevant storage bins.
     *
     * @param truck The truck to brew from.
     * @param count The number of espresso shots to brew.
     * @param ratio The espresso ratio to use.
     * @return The ingredients used mapped to their amounts.
     */
    public Map<Ingredient, Double> brewEspressoShots(Truck truck, double count, EspressoRatio ratio) {
        double coffeeBeanAmt = UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * count;
        double waterAmt = ratio.getWaterDecimal() * count;

        this.storageBinService.decreaseCapacityByTruck(truck, Ingredient.COFFEE_BEANS, coffeeBeanAmt);
        this.storageBinService.decreaseCapacityByTruck(truck, Ingredient.WATER, waterAmt);

        return Map.of(Ingredient.COFFEE_BEANS, coffeeBeanAmt, Ingredient.WATER, waterAmt);

    }

    /**
     * Asserts that a truck has the ingredients to add syrup to a coffee.
     *
     * @param truck  The truck to check.
     * @param syrup  The syrup to check against.
     * @param amount The amount of syrup to add.
     * @return Whether the truck has the ingredients to add the syrup.
     * @throws InsufficientCapacityException If the truck does not have the ingredients to add the syrup.
     */
    public boolean canAddSyrup(Truck truck, Ingredient syrup, int amount) throws InsufficientCapacityException {
        if (this.storageBinService.getTotalCapacityByTruck(truck, syrup) < 0.5) {
            throw new InsufficientCapacityException(syrup);
        }

        return true;
    }

    /**
     * Asserts that a truck has the ingredients to brew a coffee.
     *
     * @param truck  The truck to check.
     * @param coffee The coffee to check against.
     * @param size   The size of the coffee.
     * @param ratio  The espresso ratio to use.
     * @return Whether the truck has the ingredients to brew the coffee.
     * @throws InsufficientCapacityException If the truck does not have the ingredients to brew the coffee.
     */
    public boolean canBrewCoffee(Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio)
            throws InsufficientCapacityException {
        this.canBrewEspressoShots(truck, coffee.espressoRatio * size.capacity, ratio);

        double extraIngredientTotalCapacity = this.storageBinService.getTotalCapacityByTruck(truck,
                coffee.extraIngredient);

        if (coffee.extraIngredient.equals(Ingredient.WATER)
                && extraIngredientTotalCapacity < (ratio.getWaterDecimal() * coffee.espressoRatio * size.capacity)
                        + (coffee.extraIngredientRatio * size.capacity)
                || extraIngredientTotalCapacity < coffee.extraIngredientRatio * size.capacity) {
            throw new InsufficientCapacityException(coffee.extraIngredient);
        } else if (this.storageBinService.getTotalCapacityByTruck(truck, size.cup) == 0) {
            throw new InsufficientCapacityException(size.cup);
        }

        return true;
    }

    /**
     * Asserts that a truck has the ingredients to brew espresso shots.
     *
     * @param truck The coffee to check.
     * @param count The number of espresso shots to brew.
     * @param ratio The espresso ratio to use.
     * @return Whether the truck has the ingredients to brew espresso shots.
     * @throws InsufficientCapacityException If the truck does not have the ingredients to brew espresso shots.
     */
    public boolean canBrewEspressoShots(Truck truck, double count, EspressoRatio ratio)
            throws InsufficientCapacityException {
        if (this.storageBinService.getTotalCapacityByTruck(truck,
                Ingredient.COFFEE_BEANS) < UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * count) {
            throw new InsufficientCapacityException(Ingredient.COFFEE_BEANS);
        } else if (this.storageBinService.getTotalCapacityByTruck(truck, Ingredient.WATER) < ratio.getWaterDecimal()
                * count) {
            throw new InsufficientCapacityException(Ingredient.WATER);
        }

        return true;
    }

    /**
     * Checks whether a truck can brew any coffee.
     *
     * @param truck The truck to check.
     * @return Whether the truck can brew any coffee.
     */
    public boolean isCapableOfBrewing(Truck truck) {
        return this.storageBinService.truckHasIngredient(truck, Ingredient.COFFEE_BEANS, Ingredient.WATER);
    }

    /**
     * Checks whether a truck can brew a coffee.
     *
     * @param truck  The truck to check.
     * @param coffee The coffee to check against.
     * @return Whether the truck can brew the coffee.
     */
    public boolean isCapableOfBrewing(Truck truck, Coffee coffee) {
        return this.storageBinService.truckHasIngredient(truck, coffee.extraIngredient, Ingredient.COFFEE_BEANS,
                Ingredient.WATER);
    }

    /**
     * Checks whether the prices of the coffees and add-ons have been set.
     *
     * @return Whether the prices of the coffees and add-ons are not equal to {@link Coffee#DEFAULT_PRICE}.
     */
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

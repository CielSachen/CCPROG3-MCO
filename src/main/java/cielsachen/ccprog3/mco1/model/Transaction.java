package cielsachen.ccprog3.mco1.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cielsachen.ccprog3.mco1.model.coffee.CoffeeSize;

/** Represents a transaction of purchasing a product. */
public class Transaction {
    /** The name of the purchased coffee. */
    public final String coffeeName;
    /** The size of the purchased coffee. */
    public final CoffeeSize coffeeSize;
    /** The total cost of purchasing the coffee. */
    public final float cost;
    /** The number of extra espresso shots added. */
    public final int extraEspressoShotCount;
    /** The truck the coffee was purchased from. */
    public final Truck truck;

    private final Map<Ingredient, Double> ingredients;

    /**
     * Creates a new {@code Transaction} object instance.
     * 
     * @param coffeeName             The name of the purchased coffee.
     * @param coffeeSize             The size of the purchased coffee.
     * @param cost                   The total cost of purchasing the coffee.
     * @param truck                  The truck the coffee was purchased from.
     * @param extraEspressoShotCount The number of extra espresso shots added.
     * @param ingredients            The ingredients used to brew the coffee mapped to their amount.
     */
    public Transaction(String coffeeName, CoffeeSize coffeeSize, float cost, Truck truck, int extraEspressoShotCount,
            Map<Ingredient, Double> ingredients) {
        this.coffeeName = coffeeName;
        this.coffeeSize = coffeeSize;
        this.cost = cost;
        this.extraEspressoShotCount = extraEspressoShotCount;
        this.ingredients = ingredients;
        this.truck = truck;
    }

    /**
     * Gets the ingredients used to brew the coffee.
     * 
     * @return The ingredients used to brew the coffee mapped to their amount.
     */
    public Map<Ingredient, Double> getIngredients() {
        return Collections.unmodifiableMap(this.ingredients);
    }

    /**
     * Gets the syrups added to the coffee.
     * 
     * @return The syrups added to the coffee.
     */
    public List<Ingredient> getSyrups() {
        return this.ingredients.entrySet().stream().filter((entry) -> entry.getKey().isSpecial)
                .map((entry) -> entry.getKey()).toList();
    }

    /**
     * Converts the cost of the transaction into a localized string.
     * 
     * @return A localized cost string.
     */
    public String toCostString() {
        return this.cost + " PHP";
    }
}

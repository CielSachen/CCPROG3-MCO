package cielsachen.mco1.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cielsachen.mco1.model.coffee.CoffeeSize;

public class Transaction {
    public final String coffeeName;
    public final CoffeeSize coffeeSize;
    public final float cost;
    public final int extraEspressoShotCount;
    private final Map<Ingredient, Double> ingredients;
    public final Truck truck;

    public Transaction(String coffeeName, CoffeeSize coffeeSize, float cost, Truck truck, int extraEspressoShotCount,
            Map<Ingredient, Double> ingredients) {
        this.coffeeName = coffeeName;
        this.coffeeSize = coffeeSize;
        this.cost = cost;
        this.extraEspressoShotCount = extraEspressoShotCount;
        this.ingredients = ingredients;
        this.truck = truck;
    }

    public Map<Ingredient, Double> getIngredients() {
        return Collections.unmodifiableMap(this.ingredients);
    }

    public List<Ingredient> getSyrups() {
        return this.ingredients.entrySet().stream().filter((entry) -> entry.getKey().isSpecial)
                .map((entry) -> entry.getKey()).toList();
    }

    public String toCostString() {
        return this.cost + " PHP";
    }
}

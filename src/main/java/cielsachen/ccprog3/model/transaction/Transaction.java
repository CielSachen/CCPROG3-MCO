package cielsachen.ccprog3.model.transaction;

import java.util.ArrayList;
import java.util.List;

import cielsachen.ccprog3.model.Ingredient;
import cielsachen.ccprog3.model.coffee.CoffeeSize;

public class Transaction {
    public final String coffeeName;
    public final CoffeeSize coffeeSize;
    public final float cost;
    public final int extraEspressoShotAmount;
    public final int truckId;

    private final List<TransactionIngredient> ingredients = new ArrayList<TransactionIngredient>();

    public Transaction(String coffeeName, CoffeeSize coffeeSize, int extraEspressoShotAmount, float cost, int truckId,
            TransactionIngredient... transactionIngredients) {
        this.coffeeName = coffeeName;
        this.coffeeSize = coffeeSize;
        this.cost = cost;
        this.extraEspressoShotAmount = extraEspressoShotAmount;
        this.truckId = truckId;

        for (TransactionIngredient transactionIngredient : transactionIngredients) {
            this.ingredients.add(transactionIngredient);
        }
    }

    public Transaction(String coffeeName, CoffeeSize coffeeSize, float cost, int truckId,
            TransactionIngredient... transactionIngredients) {
        this(coffeeName, coffeeSize, 0, cost, truckId, transactionIngredients);
    }

    public List<TransactionIngredient> getIngredients() {
        return this.ingredients;
    }

    public List<Ingredient> getSyrups() {
        return this.ingredients.stream()
                .filter((transactionIngredient) -> transactionIngredient.ingredient.isSpecial)
                .map((transactionIngredient) -> transactionIngredient.ingredient).toList();
    }

    public String toCostString() {
        return this.cost + " PHP";
    }
}

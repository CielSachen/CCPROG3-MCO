package cielsachen.mco1.model.transaction;

import java.util.ArrayList;
import java.util.List;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.model.coffee.CoffeeSize;

public class Transaction {
    public final String coffeeName;
    public final CoffeeSize coffeeSize;
    public final float cost;
    public final int extraEspressoShotAmount;
    public final Truck truck;

    private final List<TransactionIngredient> ingredients = new ArrayList<TransactionIngredient>();

    public Transaction(String coffeeName, CoffeeSize coffeeSize, float cost, Truck truck, int extraEspressoShotAmount,
            List<TransactionIngredient> transactionIngredients) {
        this.coffeeName = coffeeName;
        this.coffeeSize = coffeeSize;
        this.cost = cost;
        this.extraEspressoShotAmount = extraEspressoShotAmount;
        this.truck = truck;

        for (TransactionIngredient transactionIngredient : transactionIngredients) {
            boolean isAdding = true;

            for (TransactionIngredient existingTransactionIngredient : this.ingredients) {
                if (existingTransactionIngredient.ingredient.equals(transactionIngredient.ingredient)) {
                    existingTransactionIngredient.increaseAmount(transactionIngredient.getAmount());

                    isAdding = false;
                }
            }

            if (isAdding) {
                this.ingredients.add(transactionIngredient);
            }
        }
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

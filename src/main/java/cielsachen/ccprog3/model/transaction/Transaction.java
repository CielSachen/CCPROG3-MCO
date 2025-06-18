package cielsachen.ccprog3.model.transaction;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public final String coffeeName;
    public final float cost;
    public final int truckId;

    private final List<TransactionIngredient> ingredients = new ArrayList<TransactionIngredient>();

    public Transaction(String coffeeName, float cost, int truckId, TransactionIngredient... ingredients) {
        this.coffeeName = coffeeName;
        this.cost = cost;
        this.truckId = truckId;

        for (TransactionIngredient ingredient : ingredients) {
            this.ingredients.add(ingredient);
        }
    }

    public List<TransactionIngredient> getIngredients() {
        return this.ingredients;
    }

    public String toCostString() {
        return this.cost + " PHP";
    }
}

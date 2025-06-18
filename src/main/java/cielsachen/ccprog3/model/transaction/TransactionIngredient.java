package cielsachen.ccprog3.model.transaction;

import cielsachen.ccprog3.model.Ingredient;

public class TransactionIngredient {
    public final double amount;
    public final Ingredient ingredient;

    public TransactionIngredient(Ingredient ingredient, double amount) {
        this.amount = amount;
        this.ingredient = ingredient;
    }
}

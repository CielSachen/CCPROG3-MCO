package cielsachen.mco1.model.transaction;

import cielsachen.mco1.model.Ingredient;

public class TransactionIngredient {
    public final Ingredient ingredient;

    private double amount;

    public TransactionIngredient(Ingredient ingredient, double amount) {
        this.amount = amount;
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean increaseAmount(double additionalAmount) {
        if (additionalAmount <= 0) {
            return false;
        }

        this.amount += additionalAmount;

        return true;
    }
}

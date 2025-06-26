package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

/** Represents an latte coffee. */
public class Latte extends Coffee {
    /** Creates a new {@code Latte} object instance. */
    public Latte() {
        super("Latte", Ingredient.MILK, 1.0 / 5.0, 4.0 / 5.0);
    }
}

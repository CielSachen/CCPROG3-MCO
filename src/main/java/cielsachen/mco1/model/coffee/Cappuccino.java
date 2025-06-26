package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

/** Represents a cappuccino coffee. */
public class Cappuccino extends Coffee {
    /** Constructs a new {@code Cappuccino} object instance. */
    public Cappuccino() {
        super("Cappuccino", Ingredient.MILK, 1.0 / 3.0, 2.0 / 3.0);
    }
}

package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

/** Represents an americano coffee. */
public class Americano extends Coffee {
    /** Constructs a new {@code Americano} object instance. */
    public Americano() {
        super("Café Americano", Ingredient.WATER, 1.0 / 3.0, 2.0 / 3.0);
    }
}

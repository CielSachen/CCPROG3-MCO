package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

public class Americano extends Coffee {
    public Americano() {
        super("Café Americano", Ingredient.WATER, 1.0 / 3.0, 2.0 / 3.0);
    }
}

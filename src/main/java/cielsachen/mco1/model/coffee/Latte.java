package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

public class Latte extends Coffee {
    public Latte() {
        super("Latte", Ingredient.MILK, 1.0 / 5.0, 4.0 / 5.0);
    }
}

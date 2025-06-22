package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

public class Cappuccino extends Coffee {
    public Cappuccino() {
        super("Cappuccino", Ingredient.MILK, 1.0 / 3.0, 2.0 / 3.0);
    }
}

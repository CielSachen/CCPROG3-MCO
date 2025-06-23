package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Product;

public abstract class Coffee extends Product {
    public static final float PRICE_SIZE_MODIFIER = 0.1f;

    public final Ingredient extraIngredient;
    public final String name;

    public final double espressoRatio;
    public final double extraIngredientRatio;

    protected Coffee(String name, Ingredient extraIngredient, double espressoRatio, double extraIngredientRatio) {
        this.espressoRatio = espressoRatio;
        this.extraIngredient = extraIngredient;
        this.extraIngredientRatio = extraIngredientRatio;
        this.name = name;
    }

    public float getPrice(CoffeeSize size) {
        switch (size) {
            case CoffeeSize.SMALL_CUP:
                return this.price - this.price * Coffee.PRICE_SIZE_MODIFIER;
            case CoffeeSize.LARGE_CUP:
                return this.price + this.price * Coffee.PRICE_SIZE_MODIFIER;
            default:
                return this.price;
        }
    }

    @Override
    public String toPriceString() {
        return "(S) " + this.getPrice(CoffeeSize.SMALL_CUP) + " PHP / (M) " + this.price + " PHP / (L) "
                + this.getPrice(CoffeeSize.LARGE_CUP) + " PHP";
    }

    public String toPriceString(CoffeeSize size) {
        return this.getPrice(size) + " PHP";
    }
}

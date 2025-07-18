package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Product;

/** Represents a brew able coffee. */
public class Coffee extends Product {
    /** The modifier for the prices of other coffee sizes. */
    private static final float PRICE_SIZE_MODIFIER = 0.1f;

    /** The extra ingredient needed to brew the coffee. */
    public final Ingredient extraIngredient;
    /** The name of the coffee. */
    public final String name;

    /** Ratio of espresso to use to brew the coffee. */
    public final double espressoRatio;
    /** Ratio of the extra ingredient to use to brew the coffee. */
    public final double extraIngredientRatio;

    /**
     * Creates a new {@code Coffee} object instance.
     * 
     * @param name                 The name of the coffee.
     * @param extraIngredient      The extra ingredient needed to brew the coffee.
     * @param espressoRatio        Ratio of espresso to use to brew the coffee.
     * @param extraIngredientRatio Ratio of the extra ingredient to use to brew the coffee.
     */
    public Coffee(String name, Ingredient extraIngredient, double espressoRatio, double extraIngredientRatio) {
        this.espressoRatio = espressoRatio;
        this.extraIngredient = extraIngredient;
        this.extraIngredientRatio = extraIngredientRatio;
        this.name = name;
    }

    /**
     * Gets the price of the coffee based on a size.
     * 
     * @param size The size to use.
     * @return The price of the coffee based on the size.
     */
    public float getPrice(CoffeeSize size) {
        return switch (size) {
            case CoffeeSize.SMALL_CUP -> this.price - this.price * Coffee.PRICE_SIZE_MODIFIER;
            case CoffeeSize.LARGE_CUP -> this.price + this.price * Coffee.PRICE_SIZE_MODIFIER;
            default -> this.price;
        };
    }

    /**
     * Converts the prices of all sizes of the coffee into a joined localized string.
     * 
     * @return A joined localized prices string.
     */
    @Override
    public String toPriceString() {
        return "(S) " + this.getPrice(CoffeeSize.SMALL_CUP) + " PHP / (M) " + this.price + " PHP / (L) "
                + this.getPrice(CoffeeSize.LARGE_CUP) + " PHP";
    }

    /**
     * Converts the price of the coffee of a size into a localized string.
     * 
     * @param size The size to use.
     * @return A localized price string.
     */
    public String toPriceString(CoffeeSize size) {
        return this.getPrice(size) + " PHP";
    }
}

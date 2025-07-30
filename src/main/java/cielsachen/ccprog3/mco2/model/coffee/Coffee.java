package cielsachen.ccprog3.mco2.model.coffee;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Product;

/** Represents a brewable coffee. */
public class Coffee extends Product {
    /** The modifier for the price of a coffee in other sizes. */
    private static final float PRICE_SIZE_MODIFIER = 0.1f;

    /** The name of the coffee. */
    public final String name;
    /** The extra ingredient needed to brew the coffee. */
    public final Ingredient extraIngredient;

    /** The ratio of espresso to use to brew the coffee. */
    public final double espressoRatio;
    /** The ratio of the extra ingredient to use to brew the coffee. */
    public final double extraIngredientRatio;

    /**
     * Creates and returns a new {@code Coffee} object instance.
     *
     * @param name                 The name of the coffee.
     * @param extraIngredient      The extra ingredient needed to brew the coffee.
     * @param espressoRatio        The ratio of espresso to use to brew the coffee.
     * @param extraIngredientRatio The ratio of the extra ingredient to use to brew the coffee.
     */
    public Coffee(String name, Ingredient extraIngredient, double espressoRatio, double extraIngredientRatio) {
        this.name = name;
        this.extraIngredient = extraIngredient;

        this.espressoRatio = espressoRatio;
        this.extraIngredientRatio = extraIngredientRatio;
    }

    /**
     * Gets the price of the coffee based on a size.
     *
     * @param size The size of the coffee.
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
     * Converts the coffee into its price string representation.
     *
     * @return The stylized prices of all sizes of the coffee.
     */
    @Override
    public String toPriceString() {
        return "(S) " + this.getPrice(CoffeeSize.SMALL_CUP) + " PHP / (M) " + this.price + " PHP / (L) "
                + this.getPrice(CoffeeSize.LARGE_CUP) + " PHP";
    }

    /**
     * Converts the coffee into its price string representation.
     *
     * @param size The size of the coffee.
     * @return The stylized price of a medium cup of the coffee.
     */
    public String toPriceString(CoffeeSize size) {
        return this.getPrice(size) + " PHP";
    }

    /**
     * Converts the coffee into its string representation.
     *
     * @return The name of the coffee.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

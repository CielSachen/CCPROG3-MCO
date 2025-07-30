package cielsachen.ccprog3.mco2.model.coffee;

import cielsachen.ccprog3.mco2.model.Ingredient;

/** Represents the ratio of coffee beans to water in a shot of espresso. */
public enum EspressoRatio {
    /** The customizable ratio (of water only) of coffee beans to water. */
    CUSTOM("Custom", 1, 0),
    /** The standard light ratio of coffee beans to water. */
    LIGHT("Light", 1, 20),
    /** The standard ratio of coffee beans to water. */
    STANDARD("Standard", 1, 18),
    /** The standard strong ratio of coffee beans to water. */
    STRONG("Strong", 1, 15);

    /** The name of the espresso ratio. */
    public final String name;

    /** The ratio of coffee beans. */
    public final double coffeeBean;
    /** The decimal ratio of coffee beans. */
    public final double coffeeBeanDecimal;

    private double water;

    private EspressoRatio(String name, double coffeeBeanRatio, double waterRatio) {
        this.name = name;

        this.coffeeBean = coffeeBeanRatio;
        this.coffeeBeanDecimal = coffeeBeanRatio / (waterRatio + 1.0);

        this.water = waterRatio;
    }

    /**
     * Gets the standard espresso ratios.
     *
     * @return The standard espresso ratios.
     */
    public static EspressoRatio[] regularValues() {
        return new EspressoRatio[] { EspressoRatio.LIGHT, EspressoRatio.STANDARD, EspressoRatio.STRONG };
    }

    /**
     * Sets the ratio of the {@link EspressoRatio#CUSTOM custom espresso ratio}.
     *
     * @param waterRatio The water ratio to use.
     */
    public static void setCustomRatio(double waterRatio) {
        EspressoRatio.CUSTOM.setWater(waterRatio);
    }

    /**
     * Gets the ratio of water.
     *
     * @return The ratio of water.
     */
    public double getWater() {
        return this.water;
    }

    /**
     * Sets the ratio of water.
     *
     * @param newWaterRatio The ratio of water to use.
     */
    private void setWater(double newWaterRatio) {
        this.water = newWaterRatio;
    }

    /**
     * Gets the decimal ratio of water.
     *
     * @return The decimal ratio of water.
     */
    public double getWaterDecimal() {
        return this.water / (this.water + 1.0);
    }

    /**
     * Converts the espresso ratio into its string representation.
     *
     * @return The stylized coffee beans to water ratio.
     */
    @Override
    public String toString() {
        if (this.equals(EspressoRatio.CUSTOM)) {
            return "Customize";
        }

        return (int) this.coffeeBean + " " + Ingredient.COFFEE_BEANS.name + " : " + (int) this.water + " "
                + Ingredient.WATER.name;
    }
}

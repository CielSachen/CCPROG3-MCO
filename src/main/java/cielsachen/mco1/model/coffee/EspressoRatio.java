package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

public enum EspressoRatio {
    CUSTOM("Custom", 1, 0),
    LIGHT("Light", 1, 20),
    STANDARD("Standard", 1, 18),
    STRONG("Strong", 1, 15);

    public final double coffeeBean;
    public final double coffeeBeanDecimal;
    public final String name;

    private double water;

    private EspressoRatio(String name, double coffeeBeanRatio, double waterRatio) {
        this.coffeeBean = coffeeBeanRatio;
        this.coffeeBeanDecimal = coffeeBeanRatio / (waterRatio + 1.0);
        this.name = name;
        this.water = waterRatio;
    }

    public static EspressoRatio[] regularValues() {
        return new EspressoRatio[] { EspressoRatio.LIGHT, EspressoRatio.STANDARD, EspressoRatio.STRONG };
    }

    public static void setCustomRatio(double waterRatio) {
        EspressoRatio.CUSTOM.setWater(waterRatio);
    }

    public double getWater() {
        return this.water;
    }

    private void setWater(double newWaterRatio) {
        this.water = newWaterRatio;
    }

    public double getWaterDecimal() {
        return this.water / (this.water + 1.0);
    }

    @Override
    public String toString() {
        if (this.equals(EspressoRatio.CUSTOM)) {
            return "TBD";
        }

        return (int) this.coffeeBean + " " + Ingredient.COFFEE_BEANS.name + " : " + (int) this.water + " "
                + Ingredient.WATER.name;
    }
}

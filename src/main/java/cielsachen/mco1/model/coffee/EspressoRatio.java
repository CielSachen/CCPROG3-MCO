package cielsachen.mco1.model.coffee;

public enum EspressoRatio {
    LIGHT("Light", 1, 20),
    STANDARD("Standard", 1, 18),
    STRONG("Strong", 1, 15);

    public final double coffeeBean;
    public final double coffeeBeanDecimal;
    public final String name;
    public final double water;
    public final double waterDecimal;

    private EspressoRatio(String name, double coffeeBean, double water) {
        this.coffeeBean = coffeeBean;
        this.coffeeBeanDecimal = coffeeBean / (water + 1.0);
        this.name = name;
        this.water = water;
        this.waterDecimal = water / (water + 1.0);
    }

    @Override
    public String toString() {
        return this.coffeeBean + " / " + this.water;
    }
}

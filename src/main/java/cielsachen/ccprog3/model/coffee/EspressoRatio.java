package cielsachen.ccprog3.model.coffee;

public enum EspressoRatio {
    LIGHT(1, 20),
    STANDARD(1, 18),
    STRONG(1, 15);

    public final double coffeeBean;
    public final double coffeeBeanDecimal;
    public final double water;
    public final double waterDecimal;

    private EspressoRatio(double coffeeBean, double water) {
        this.coffeeBean = coffeeBean;
        this.coffeeBeanDecimal = coffeeBean / water + 1;
        this.water = water;
        this.waterDecimal = water / water + 1;
    }

    @Override
    public String toString() {
        return this.coffeeBean + " / " + this.water;
    }
}

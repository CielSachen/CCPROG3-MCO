package cielsachen.ccprog3.model;

public enum Ingredient {
    SMALL_CUP("Small Cup", 80, "pcs"),
    MEDIUM_CUP("Medium Cup", 64, "pcs"),
    LARGE_CUP("Large Cup", 40, "pcs"),
    COFFEE_BEANS("Coffee Beans", 1008, "g"),
    MILK("Milk", 640, "fl oz"),
    WATER("Water", 640, "fl oz"),

    HAZELNUT_SYRUP("Hazelnut Syrup", 640, "fl oz"),
    CHOCOLATE_SYRUP("Chocolate Syrup", 640, "fl oz"),
    ALMOND_SYRUP("Almond Syrup", 640, "fl oz"),
    SWEETENER("Sweetener (Sucrose)", 640, "fl oz");

    public final double maximumCapacity;
    public final String name;
    public final String unitMeasure;

    private Ingredient(String name, double maximumCapacity, String unitMeasure) {
        this.maximumCapacity = maximumCapacity;
        this.name = name;
        this.unitMeasure = unitMeasure;
    }

    public String toMaximumCapacityString() {
        return this.maximumCapacity + " " + this.unitMeasure;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

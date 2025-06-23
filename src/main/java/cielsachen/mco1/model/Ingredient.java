package cielsachen.mco1.model;

import java.util.Arrays;
import java.util.List;

public enum Ingredient {
    SMALL_CUP("Small Cups", 80, "pcs"),
    MEDIUM_CUP("Medium Cups", 64, "pcs"),
    LARGE_CUP("Large Cups", 40, "pcs"),
    COFFEE_BEANS("Coffee Beans", 1008, "g"),
    MILK("Milk", 640, "fl oz"),
    WATER("Water", 640, "fl oz"),

    HAZELNUT_SYRUP("Hazelnut Syrup", true, 640, "fl oz"),
    CHOCOLATE_SYRUP("Chocolate Syrup", true, 640, "fl oz"),
    ALMOND_SYRUP("Almond Syrup", true, 640, "fl oz"),
    SWEETENER("Sweetener (Sucrose)", true, 640, "fl oz");

    public final boolean isSpecial;
    public final double maximumCapacity;
    public final String name;
    public final String unitMeasure;

    private Ingredient(String name, boolean isSpecial, double maximumCapacity, String unitMeasure) {
        this.isSpecial = isSpecial;
        this.maximumCapacity = maximumCapacity;
        this.name = name;
        this.unitMeasure = unitMeasure;
    }

    private Ingredient(String name, double maximumCapacity, String unitMeasure) {
        this(name, false, maximumCapacity, unitMeasure);
    }

    public static List<Ingredient> regularValues() {
        return Arrays.stream(Ingredient.values()).filter((ingredient) -> !ingredient.isSpecial).toList();
    }

    public static List<Ingredient> specialValues() {
        return Arrays.stream(Ingredient.values()).filter((ingredient) -> ingredient.isSpecial).toList();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

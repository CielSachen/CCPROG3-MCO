package cielsachen.ccprog3.mco1.model;

import java.util.Arrays;
import java.util.List;

/** Represents an ingredient for brewing coffee. */
public enum Ingredient {
    /** A small cup to store coffee for drinking. */
    SMALL_CUP("Small Cups", 80, "pcs"),
    /** A medium cup to store coffee for drinking. */
    MEDIUM_CUP("Medium Cups", 64, "pcs"),
    /** A large cup to store coffee for drinking. */
    LARGE_CUP("Large Cups", 40, "pcs"),
    /** Coffee beans used for brewing coffee. */
    COFFEE_BEANS("Coffee Beans", 1008, "g"),
    /** Milk used for brewing coffee. */
    MILK("Milk", 640, "fl oz"),
    /** Water used for brewing coffee. */
    WATER("Water", 640, "fl oz"),

    /** Hazelnut syrup that can be added to a coffee. */
    HAZELNUT_SYRUP("Hazelnut Syrup", true, 640, "fl oz"),
    /** Chocolate syrup that can be added to a coffee. */
    CHOCOLATE_SYRUP("Chocolate Syrup", true, 640, "fl oz"),
    /** Almond syrup that can be added to a coffee. */
    ALMOND_SYRUP("Almond Syrup", true, 640, "fl oz"),
    /** Sweetener that can be added to a coffee. */
    SWEETENER("Sweetener (Sucrose)", true, 640, "fl oz");

    /** Whether the ingredient is for special trucks only. */
    public final boolean isSpecial;
    /** The maximum storage bin capacity of the ingredient. */
    public final double maximumCapacity;
    /** The name of the ingredient. */
    public final String name;
    /** The unit of measure for a quantity of the ingredient. */
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

    /**
     * Gets the ingredients that can be used by any truck.
     * 
     * @return The non-special ingredients.
     */
    public static List<Ingredient> regularValues() {
        return Arrays.stream(Ingredient.values()).filter((ingredient) -> !ingredient.isSpecial).toList();
    }

    /**
     * Gets the ingredients that can only be used by special trucks.
     * 
     * @return The special ingredients.
     */
    public static List<Ingredient> specialValues() {
        return Arrays.stream(Ingredient.values()).filter((ingredient) -> ingredient.isSpecial).toList();
    }

    /**
     * Gets the name of the ingredient.
     * 
     * @return The name of the ingredient.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

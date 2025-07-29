package cielsachen.ccprog3.mco2.model.coffee;

import cielsachen.ccprog3.mco2.model.Ingredient;

/** Represents a size of coffee. */
public enum CoffeeSize {
    /** A small cup of coffee. */
    SMALL_CUP(8, Ingredient.SMALL_CUP),
    /** A medium cup of coffee. */
    MEDIUM_CUP(12, Ingredient.MEDIUM_CUP),
    /** A large cup of coffee. */
    LARGE_CUP(16, Ingredient.LARGE_CUP);

    /** The capacity of a cup of the coffee size. */
    public final int capacity;
    /** The cup of the coffee size. */
    public final Ingredient cup;
    /** The unit of measure for the capacity of the cup. */
    public final String unitMeasure = "fl oz";

    private CoffeeSize(int capacity, Ingredient cup) {
        this.capacity = capacity;
        this.cup = cup;
    }

    /**
     * Converts the capacity of a cup of the coffee size into a quantity string.
     *
     * @return A quantity string.
     */
    @Override
    public String toString() {
        return this.capacity + " " + this.unitMeasure;
    }
}

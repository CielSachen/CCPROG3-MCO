package cielsachen.ccprog3.mco2.model.coffee;

import cielsachen.ccprog3.mco2.model.Ingredient;

/** Represents the size of a coffee. */
public enum CoffeeSize {
    /** A small cup of coffee. */
    SMALL_CUP(8, Ingredient.SMALL_CUP),
    /** A medium cup of coffee. */
    MEDIUM_CUP(12, Ingredient.MEDIUM_CUP),
    /** A large cup of coffee. */
    LARGE_CUP(16, Ingredient.LARGE_CUP);

    /** The cup of the size. */
    public final Ingredient cup;
    /** The capacity of a cup of the size. */
    public final int capacity;
    /** The unit of measure for the capacity of the cup. */
    public final String unitMeasure = "fl oz";

    private CoffeeSize(int capacity, Ingredient cup) {
        this.cup = cup;
        this.capacity = capacity;
    }

    /**
     * Converts the size into its string representation.
     *
     * @return The stylized capacity of a cup of the size.
     */
    @Override
    public String toString() {
        return this.capacity + " " + this.unitMeasure;
    }
}

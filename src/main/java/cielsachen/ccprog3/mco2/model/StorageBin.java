package cielsachen.ccprog3.mco2.model;

/** Represents a bin storing an ingredient on a truck. */
public class StorageBin {
    /** The number of storage bins a standard truck has. */
    public static final int STANDARD_TRUCK_COUNT = 8;
    /** The number of storage bins a special truck has. */
    public static final int SPECIAL_TRUCK_COUNT = 10;

    /** The one-based ID of the storage bin. */
    public final int id;
    /** The truck the storage bin belongs to. */
    public final Truck truck;

    private Ingredient ingredient;
    private double capacity = 0;

    /**
     * Creates and returns a new {@code StorageBin} object instance.
     *
     * @param id         The ID of the storage bin.
     * @param truck      The truck the storage bin belongs to.
     * @param ingredient The ingredient the storage bin contains.
     */
    public StorageBin(int id, Truck truck, Ingredient ingredient) {
        this.id = id;
        this.truck = truck;

        this.ingredient = ingredient;
        this.capacity = ingredient.maximumCapacity;
    }

    /**
     * Gets the capacity of the storage bin.
     *
     * @return The capacity of the storage bin.
     */
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Gets ingredient the storage bin contains.
     *
     * @return The ingredient the storage bin contains.
     */
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    /**
     * Sets ingredient the storage bin will contain.
     *
     * @param newIngredient The new ingredient to contain.
     */
    public void setIngredient(Ingredient newIngredient) {
        this.ingredient = newIngredient;
    }

    /**
     * Decreases the capacity of the storage bin.
     *
     * @param deductibleCapacity The amount to decrease by. If the amount is greater than the current capacity, the
     *                           capacity will not be decreased.
     * @return Whether the capacity of the storage bin was decreased.
     */
    public boolean decreaseCapacity(double deductibleCapacity) {
        if (deductibleCapacity > this.capacity) {
            return false;
        }

        this.capacity -= deductibleCapacity;

        return true;
    }

    /**
     * Increases the capacity of the storage bin.
     *
     * @param additionalCapacity The amount to increase by. If the amount is zero or negative, capacity will not be
     *                           increased.
     * @return Whether the capacity of the storage bin was increased.
     */
    public boolean increaseCapacity(double additionalCapacity) {
        if (additionalCapacity <= 0) {
            return false;
        }

        this.capacity += additionalCapacity;

        return true;
    }

    /**
     * Checks whether the capacity of the storage bin is at a critical-level.
     *
     * @return Whether the capacity of the storage bin is less than a third of the maximum.
     */
    public boolean isCriticalCapacity() {
        return this.capacity < this.ingredient.maximumCapacity / 3;
    }

    /**
     * Converts the storage bin into its capacity string representation.
     *
     * @return The stylized capacity of the storage bin.
     */
    public String toCapacityString() {
        return String.format("%.2f / ", this.capacity) + (int) this.ingredient.maximumCapacity + " "
                + this.ingredient.unitMeasure;
    }

    /**
     * Converts the storage bin into its string representation.
     *
     * @return The ID of the storage bin.
     */
    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}

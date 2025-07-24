package cielsachen.ccprog3.mco1.model;

/** Represents a bin storing an ingredient on a truck. */
public class StorageBin {
    /** The number of storage bins a standard truck has. */
    public static final int STANDARD_TRUCK_COUNT = 8;
    /** The number of storage bins a special truck has. */
    public static final int SPECIAL_TRUCK_COUNT = 10;

    /** The ID of the storage bin (one-based). */
    public final int id;
    /** The truck that the storage bin belongs to. */
    public final Truck truck;

    private double capacity = 0;
    private Ingredient ingredient;

    /**
     * Creates a new {@code StorageBin} object instance.
     * 
     * @param id         The ID of the storage bin.
     * @param truck      The truck that the storage bin belongs to.
     * @param ingredient The ingredient the storage bin contains.
     */
    public StorageBin(int id, Truck truck, Ingredient ingredient) {
        this.capacity = ingredient.maximumCapacity;
        this.id = id;
        this.ingredient = ingredient;
        this.truck = truck;
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
     * @param newIngredient The new ingredient to use.
     */
    public void setIngredient(Ingredient newIngredient) {
        this.ingredient = newIngredient;
    }

    /**
     * Decreases the capacity of the storage bin.
     * <p>
     * The capacity cannot be less than zero.
     * 
     * @param deductibleCapacity The amount to decrease by.
     * @return Whether the capacity was decreased.
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
     * <p>
     * The capacity cannot be greater than the ingredient's maximum.
     * 
     * @param additionalCapacity The amount to increase by.
     * @return Whether the capacity was increased.
     */
    public boolean increaseCapacity(double additionalCapacity) {
        if (additionalCapacity <= 0) {
            return false;
        }

        this.capacity += additionalCapacity;

        return true;
    }

    /**
     * Checks if the capacity of the storage bin is at critical levels.
     * 
     * @return Whether the capacity is less than a third of the maximum.
     */
    public boolean isCriticalCapacity() {
        return this.capacity < this.ingredient.maximumCapacity / 3;
    }

    /**
     * Converts the capacities of the storage bin into a capacity fraction string.
     * 
     * @return A capacity string.
     */
    public String toCapacityString() {
        return String.format("%.2f / ", this.capacity) + (int) this.ingredient.maximumCapacity + " "
                + this.ingredient.unitMeasure;
    }
}

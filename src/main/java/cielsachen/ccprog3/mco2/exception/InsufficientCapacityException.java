package cielsachen.ccprog3.mco2.exception;

import cielsachen.ccprog3.mco2.model.Ingredient;

/** Represents an exception when a storage bin has insufficient capacity for an operation. */
public class InsufficientCapacityException extends Exception {
    /** The ingredient of the storage bin. */
    public final Ingredient ingredient;

    /**
     * Creates and returns a new {@code InsufficientCapacityException} object instance.
     *
     * @param ingredient The ingredient of the storage bin.
     */
    public InsufficientCapacityException(Ingredient ingredient) {
        super();

        this.ingredient = ingredient;
    }
}

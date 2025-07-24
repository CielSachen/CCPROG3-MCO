package cielsachen.ccprog3.mco1.exception;

import cielsachen.ccprog3.mco1.model.Ingredient;

/** Represents an exception when storage bins has insufficient capacity. */
public class InsufficientCapacityException extends Exception {
    /** The ingredient of the storage bins. */
    public final Ingredient ingredient;

    /**
     * Creates a new {@code InsufficientCapacityException} object instance.
     * 
     * @param ingredient The ingredient of the storage bins.
     */
    public InsufficientCapacityException(Ingredient ingredient) {
        super("A truck has emptied all storage bins for an ingredient.");

        this.ingredient = ingredient;
    }
}

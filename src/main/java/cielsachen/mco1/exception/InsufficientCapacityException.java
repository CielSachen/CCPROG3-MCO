package cielsachen.mco1.exception;

import cielsachen.mco1.model.Ingredient;

public class InsufficientCapacityException extends Exception {
    public final Ingredient ingredient;

    public InsufficientCapacityException(Ingredient ingredient) {
        super("A truck has emptied all storage bins for an ingredient.");

        this.ingredient = ingredient;
    }
}

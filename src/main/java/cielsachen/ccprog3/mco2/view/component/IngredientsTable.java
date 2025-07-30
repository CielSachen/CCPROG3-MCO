package cielsachen.ccprog3.mco2.view.component;

import java.util.Map;

import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;

/** Represents a table containing information about ingredients. */
public class IngredientsTable extends JTable {
    /**
     * Creates and returns a new {@code IngredientsTable} object instance.
     *
     * @param amountsByIngredient The ingredients mapped to their amounts.
     */
    public IngredientsTable(Map<Ingredient, Double> amountsByIngredient) {
        super(amountsByIngredient.entrySet().stream().map((entry) -> {
            Ingredient ingredient = entry.getKey();

            return new String[] {
                    ingredient.name,
                    String.format("%.2f " + ingredient.unitMeasure, entry.getValue()),
            };
        }).toArray(String[][]::new), new String[] { "Ingredient", "Amount" });
    }

    /**
     * Creates and returns a new {@code IngredientsTable} object instance.
     *
     * @param transaction The transaction to use.
     */
    public IngredientsTable(Transaction transaction) {
        this(transaction.getIngredients());
    }
}

package cielsachen.ccprog3.mco2.view.component;

import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;

public class IngredientTable extends JTable {
    public IngredientTable(Transaction transaction) {
        super(
                transaction.getIngredients().entrySet().stream().map((entry) -> {
                    Ingredient ingredient = entry.getKey();

                    return new String[] {
                            ingredient.name,
                            String.format("&.2f " + ingredient.unitMeasure, entry.getValue()),
                    };
                }).toArray(String[][]::new),
                new String[] { "Ingredient", "Amount" });
    }
}

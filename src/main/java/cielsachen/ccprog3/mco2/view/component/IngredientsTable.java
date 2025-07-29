package cielsachen.ccprog3.mco2.view.component;

import java.util.Map;

import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;

public class IngredientsTable extends JTable {
    public IngredientsTable(Map<Ingredient, Double> amountsByIngredient) {
        super(
                amountsByIngredient.entrySet().stream().map((entry) -> {
                    Ingredient ingredient = entry.getKey();

                    return new String[] {
                            ingredient.name,
                            String.format("%.2f " + ingredient.unitMeasure, entry.getValue()),
                    };
                }).toArray(String[][]::new),
                new String[] { "Ingredient", "Amount" });
    }

    public IngredientsTable(Transaction transaction) {
        this(transaction.getIngredients());
    }
}

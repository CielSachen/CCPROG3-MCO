package cielsachen.ccprog3.mco2.view.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;

public class TransactionPanel extends JPanel {
    public TransactionPanel(Transaction transaction) {
        super();

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("ID: " + transaction), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(new JLabel("Truck ID: " + transaction.truck.id), constraints);

        constraints.gridy++;

        super.add(new JLabel("Coffee: " + transaction.coffeeName), constraints);

        constraints.gridy++;
        constraints.insets.top = 4;

        super.add(new JLabel("Ingredients"), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        String[][] tableData = transaction.getIngredients().entrySet().stream().map((entry) -> {
            Ingredient ingredient = entry.getKey();

            return new String[] {
                    ingredient.name,
                    String.format("&.2f " + ingredient.unitMeasure, entry.getValue()),
            };
        }).toArray(String[][]::new);

        super.add(new JScrollPane(new JTable(tableData, new String[] { "Ingredient", "Amount" })), constraints);
    }
}

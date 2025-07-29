package cielsachen.ccprog3.mco2.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.view.component.TransactionPanel;

public class DashboardView extends JFrame {
    public DashboardView(JFrame parentFrame, int truckCnt, int specialTruckCnt,
            Map<Ingredient, Double> capacitiesByIngredient, List<Transaction> transactions) {
        super("Dashboard");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Deployed Trucks: " + truckCnt), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(new JLabel("Special Trucks: " + specialTruckCnt), constraints);

        constraints.gridy++;
        constraints.insets.top = 4;

        super.add(new JLabel("Ingredients"), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        String[][] tableData = capacitiesByIngredient.entrySet().stream().map((entry) -> {
            Ingredient ingredient = entry.getKey();

            return new String[] {
                    ingredient.name,
                    String.format("%.2f " + ingredient.unitMeasure, entry.getValue()) };
        }).toArray(String[][]::new);

        super.add(new JScrollPane(new JTable(tableData, new String[] { "Ingredient", "Amount" })), constraints);

        constraints.insets.bottom = constraints.insets.top;

        for (int i = 0; i < transactions.size(); i++) {
            constraints.gridy++;

            super.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);

            constraints.gridy++;

            if (i < transactions.size() - 1) {
                constraints.insets.bottom = 20;
            }

            super.add(new TransactionPanel(transactions.get(i)), constraints);
        }

        super.pack();
        super.setVisible(true);
    }
}

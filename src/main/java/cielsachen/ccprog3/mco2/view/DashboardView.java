package cielsachen.ccprog3.mco2.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.util.TableSize;
import cielsachen.ccprog3.mco2.view.component.IngredientsTable;

public class DashboardView extends JFrame {
    public DashboardView(JFrame parentFrame, int truckCnt, int specialTruckCnt,
            Map<Ingredient, Double> capacitiesByIngredient, List<Transaction> transactions) {
        super("Dashboard");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        var panel = new JPanel(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        panel.add(new JLabel("Deployed Trucks: " + truckCnt), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        panel.add(new JLabel("Special Trucks: " + specialTruckCnt), constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 4;

        panel.add(new JLabel("Ingredients"), constraints);

        var ingredientsTablePane = new JScrollPane(new JTable(
                capacitiesByIngredient.entrySet().stream().map((entry) -> {
                    Ingredient ingredient = entry.getKey();

                    return new String[] {
                            ingredient.name,
                            String.format("%.2f " + ingredient.unitMeasure, entry.getValue()) };
                }).toArray(String[][]::new),
                new String[] { "Ingredient", "Amount" }));
        ingredientsTablePane.setPreferredSize(TableSize.MEDIUM.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        panel.add(ingredientsTablePane, constraints);

        for (int i = 0; i < transactions.size(); i++) {
            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom = 0;

            panel.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 0;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets.top = 12;
            constraints.insets.bottom = 2;

            panel.add(new JLabel("Transaction #: " + (i + 1)), constraints);

            Transaction transaction = transactions.get(i);

            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom;

            panel.add(new JLabel("Truck ID: " + transaction.truck.id), constraints);

            constraints.gridy++;

            panel.add(new JLabel("Coffee: " + transaction.coffeeName), constraints);

            constraints.gridy++;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets.top = 4;

            panel.add(new JLabel("Ingredients"), constraints);

            ingredientsTablePane = new JScrollPane(new IngredientsTable(transaction));
            ingredientsTablePane.setPreferredSize(TableSize.SMALL.dimension);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets.top = constraints.insets.bottom;
            constraints.insets.bottom = 20;

            panel.add(ingredientsTablePane, constraints);
        }

        super.add(new JScrollPane(panel));

        super.pack();
        super.setMaximumSize(new Dimension(super.getSize().width, 660));
        super.setVisible(true);
    }
}

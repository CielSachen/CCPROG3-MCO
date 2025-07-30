package cielsachen.ccprog3.mco2.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Product;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.view.component.CoffeePricesTable;
import cielsachen.ccprog3.mco2.view.component.IngredientsTable;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing information about a truck. */
public class TruckView extends JFrame {
    /**
     * Creates and returns a new {@code TruckView} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param truck           The truck to use.
     * @param storageBins     The storage bins of the truck to use.
     * @param coffees         The coffees the truck to use can brew.
     * @param espresso        The addable extra espresso shot.
     * @param syrup           The addable syrup add-on.
     * @param transactions    The transactions linked with the truck to use.
     */
    public TruckView(Component parentComponent, Truck truck, List<StorageBin> storageBins, List<Coffee> coffees,
            Product espresso, Product syrup, List<Transaction> transactions) {
        super("Truck #" + truck.id);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        var panel = new JPanel(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        panel.add(new JLabel("ID: " + truck.id), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        panel.add(new JLabel("Special Truck: " + (truck.isSpecial ? "Yes" : "No")), constraints);

        constraints.gridy++;

        panel.add(new JLabel("Location: " + truck.getLocation()), constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 4;

        panel.add(new JLabel("Storage Bins"), constraints);

        var storageBinsTablePane = new JScrollPane(new JTable(storageBins.stream().map((storageBin) -> {
            Ingredient ingredient = storageBin.getIngredient();

            return new String[] { Integer.toString(storageBin.id), ingredient.name,
                    String.format("%.2f " + ingredient.unitMeasure, storageBin.getCapacity()),
                    String.format("%.2f " + ingredient.unitMeasure, ingredient.maximumCapacity) };
        }).toArray(String[][]::new), new String[] { "ID", "Ingredient", "Current Capacity", "Maximum Capacity" }));
        storageBinsTablePane.setPreferredSize(TableSize.LARGE.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        panel.add(storageBinsTablePane, constraints);

        if (coffees != null && espresso != null && syrup != null) {
            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom = 0;

            panel.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 0.0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets.top = 12;
            constraints.insets.bottom = 2;

            panel.add(new JLabel("Prices"), constraints);

            var coffeePricesTablePane = new JScrollPane(new CoffeePricesTable(coffees));
            coffeePricesTablePane.setPreferredSize(TableSize.LARGE.dimension);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets.top = constraints.insets.bottom;

            if (!truck.isSpecial) {
                constraints.insets.bottom = 20;
            }

            panel.add(coffeePricesTablePane, constraints);

            if (truck.isSpecial) {
                constraints.gridy++;
                constraints.weightx = constraints.weighty = 0.0;
                constraints.fill = GridBagConstraints.NONE;

                panel.add(new JLabel("Extra Espresso Shots: " + espresso.toPriceString()), constraints);

                constraints.gridy++;
                constraints.insets.bottom = 20;

                panel.add(new JLabel("Add-On Syrups: " + syrup.toPriceString()), constraints);
            }
        }

        if (transactions != null) {
            constraints.weightx = constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;

            for (int i = 0; i < transactions.size(); i++) {
                constraints.gridy++;
                constraints.insets.top = constraints.insets.bottom = 0;

                panel.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);

                constraints.gridy++;
                constraints.weightx = constraints.weighty = 0.0;
                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.insets.top = 12;
                constraints.insets.bottom = 2;

                panel.add(new JLabel("Transaction #: " + (i + 1)), constraints);

                Transaction transaction = transactions.get(i);

                constraints.gridy++;
                constraints.insets.top = constraints.insets.bottom;

                panel.add(new JLabel("Coffee: " + transaction.coffeeName), constraints);

                constraints.gridy++;
                constraints.fill = GridBagConstraints.NONE;
                constraints.insets.top = 4;

                panel.add(new JLabel("Ingredients"), constraints);

                var ingredientsTablePane = new JScrollPane(new IngredientsTable(transaction));
                ingredientsTablePane.setPreferredSize(TableSize.SMALL.dimension);

                constraints.gridy++;
                constraints.weightx = constraints.weighty = 1.0;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.insets.top = constraints.insets.bottom;
                constraints.insets.bottom = 20;

                panel.add(ingredientsTablePane, constraints);
            }
        }

        super.add(new JScrollPane(panel));

        super.pack();
        super.setMaximumSize(new Dimension(super.getSize().width, 660));
        super.setVisible(true);
    }

    public TruckView(Component parentComponent, Truck truck, List<StorageBin> storageBins, List<Coffee> coffees,
            Product espresso, Product syrup) {
        this(parentComponent, truck, storageBins, coffees, espresso, syrup, null);
    }

    public TruckView(Component parentComponent, Truck truck, List<StorageBin> storageBins) {
        this(parentComponent, truck, storageBins, null, null, null);
    }
}

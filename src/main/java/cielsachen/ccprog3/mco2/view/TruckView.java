package cielsachen.ccprog3.mco2.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Product;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.view.component.CoffeePricesTable;

public class TruckView extends JFrame {
    public TruckView(JFrame parentFrame, Truck truck, List<StorageBin> storageBins, List<Coffee> coffees,
            Product extraEspressoShot, Product syrupAddOn) {
        super("Truck #" + truck.id);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("ID: " + truck.id), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(new JLabel("Special Truck: " + (truck.isSpecial ? "Yes" : "No")), constraints);

        constraints.gridy++;

        super.add(new JLabel("Location: " + truck.getLocation()), constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 4;

        super.add(new JLabel("Storage Bins"), constraints);

        var storageBinsTablePanel = new JScrollPane(new JTable(
                storageBins.stream().map((storageBin) -> {
                    Ingredient ingredient = storageBin.getIngredient();

                    return new String[] {
                            ingredient.name,
                            String.format("%.2f " + ingredient.unitMeasure, storageBin.getCapacity()),
                            String.format("%.2f " + ingredient.unitMeasure, ingredient.maximumCapacity) };
                }).toArray(String[][]::new),
                new String[] { "Ingredient", "Current Capacity", "Maximum Capacity" }));
        storageBinsTablePanel.setPreferredSize(new Dimension(430, 183));

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(storageBinsTablePanel, constraints);

        if (coffees != null && extraEspressoShot != null && syrupAddOn != null) {
            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom = 0;

            super.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets.top = 12;
            constraints.insets.bottom = 2;

            super.add(new JLabel("Prices"), constraints);

            var coffeePricesTablePanel = new JScrollPane(new CoffeePricesTable(coffees));
            coffeePricesTablePanel.setPreferredSize(new Dimension(430, 71));

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets.top = constraints.insets.bottom;

            super.add(coffeePricesTablePanel, constraints);

            constraints.gridy++;
            constraints.weightx = constraints.weighty = 0;
            constraints.fill = GridBagConstraints.NONE;

            super.add(new JLabel("Extra Espresso Shots: " + extraEspressoShot.toPriceString()), constraints);

            constraints.gridy++;
            constraints.insets.bottom = 20;

            super.add(new JLabel("Add-On Syrups: " + syrupAddOn.toPriceString()), constraints);
        }

        super.pack();
        super.setVisible(true);
    }

    public TruckView(JFrame parentFrame, Truck truck, List<StorageBin> storageBins) {
        this(parentFrame, truck, storageBins, null, null, null);
    }
}

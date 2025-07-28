package cielsachen.ccprog3.mco2.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.view.component.CoffeePricesTable;

public class TruckView extends JFrame {
    public TruckView(JFrame parentFrame, Truck truck, List<StorageBin> storageBins, List<Coffee> coffees,
            String espressoPriceString, String syrupPriceString) {
        super("Truck #" + truck.id);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = constraints.gridy = 0;
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

        var storageBinsTablePane = new JScrollPane(new JTable(
                storageBins.stream().map((storageBin) -> {
                    Ingredient ingredient = storageBin.getIngredient();

                    return new String[] {
                            ingredient.name,
                            String.format("%.2f " + ingredient.unitMeasure, storageBin.getCapacity()),
                            String.format("%.2f " + ingredient.unitMeasure, ingredient.maximumCapacity) };
                }).toArray(String[][]::new),
                new String[] { "Ingredient", "Current Capacity", "Maximum Capacity" }));

        constraints.gridy++;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(storageBinsTablePane, constraints);

        if (coffees != null && espressoPriceString != null && syrupPriceString != null) {
            constraints.gridy++;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = constraints.weighty = 0.0;
            constraints.insets.bottom = constraints.insets.top;

            super.add(new JLabel("Prices"), constraints);

            constraints.gridy++;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 0.5;
            constraints.insets.top = constraints.insets.bottom;

            super.add(
                    new JScrollPane(new CoffeePricesTable(coffees, espressoPriceString, syrupPriceString)),
                    constraints);

            constraints.gridy++;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = constraints.weighty = 0.0;

            super.add(new JLabel("Extra Espresso Shots: " + espressoPriceString), constraints);

            constraints.gridy++;
            constraints.insets.bottom = 20;

            super.add(new JLabel("Add-On Syrups: " + syrupPriceString), constraints);
        }

        super.pack();
        super.setVisible(true);
    }

    public TruckView(JFrame parentFrame, Truck truck, List<StorageBin> storageBins) {
        this(parentFrame, truck, storageBins, null, null, null);
    }
}

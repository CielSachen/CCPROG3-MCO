package cielsachen.ccprog3.mco2.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.view.component.IngredientsTable;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing information about a brewed coffee. */
public class BrewCompletionView extends JFrame {
    /**
     * Creates and returns a new {@code BrewCompletionView} object instance.
     *
     * @param parentComponent       The parent component of the window.
     * @param coffee                The brewed coffee.
     * @param ratio                 The espresso ratio of the brewed coffee.
     * @param size                  The size of the brewed coffee.
     * @param extraEspressoShotsCnt The number of extra espresso shots added.
     * @param amountsByIngredient   The ingredients used to brew the coffee mapped to their amounts.
     * @param totalCost             The total cost of purchasing the brewed coffee.
     */
    public BrewCompletionView(Component parentComponent, Coffee coffee, EspressoRatio ratio, CoffeeSize size,
            int extraEspressoShotsCnt, Map<Ingredient, Double> amountsByIngredient, double totalCost) {
        super("Brewing Completed");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Prepared Coffee: " + coffee.name), constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(new JLabel("Espresso Ratio: " + ratio.name), constraints);

        constraints.gridy++;

        super.add(new JLabel("Extra Espresso Shots: " + extraEspressoShotsCnt), constraints);

        constraints.gridy++;

        super.add(new JLabel("With Syrup: " + amountsByIngredient.keySet().stream().anyMatch((i) -> i.isSpecial)),
                constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets.top = 4;

        super.add(new JLabel("Ingredients Used"), constraints);

        var ingredientsTablePane = new JScrollPane(new IngredientsTable(amountsByIngredient));
        ingredientsTablePane.setPreferredSize(TableSize.SMALL.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(ingredientsTablePane, constraints);

        super.pack();
        super.setVisible(true);
    }
}

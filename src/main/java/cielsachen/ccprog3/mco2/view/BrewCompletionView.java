package cielsachen.ccprog3.mco2.view;

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

public class BrewCompletionView extends JFrame {
    /**
     * Creates and returns a new {@code BrewCompletionView} object instance.
     *
     * @param parentFrame
     * @param coffee
     * @param ratio
     * @param size
     * @param extraEspressoShotsCnt
     * @param amountsByIngredient
     * @param totalCost
     */
    public BrewCompletionView(JFrame parentFrame, Coffee coffee, EspressoRatio ratio, CoffeeSize size,
            int extraEspressoShotsCnt, Map<Ingredient, Double> amountsByIngredient, double totalCost) {
        super("Brewing Completed");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

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

        super.add(
                new JLabel("With Syrup: " + amountsByIngredient.keySet().stream().anyMatch((i) -> i.isSpecial)),
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

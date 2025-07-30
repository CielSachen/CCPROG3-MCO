package cielsachen.ccprog3.mco2.view.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing a form for selecting a coffee size. */
public class CoffeeSizeSelectionForm extends JFrame {
    /** The selection of coffee sizes to select from. */
    public final JComboBox<CoffeeSize> coffeeSizeComboBox;
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code CoffeeSizeSelectionForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param coffeeSizes     The coffee sizes to use.
     */
    public CoffeeSizeSelectionForm(Component parentComponent, CoffeeSize[] coffeeSizes) {
        super("Coffee Size Selection");

        this.coffeeSizeComboBox = new JComboBox<CoffeeSize>(coffeeSizes);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please select the size of the coffee to brew…"), constraints);

        var coffeeSizesTablePane = new JScrollPane(new JTable(
                Arrays.stream(coffeeSizes).map((cf) -> new String[] { cf.cup.name, cf.toString() })
                        .toArray(String[][]::new),
                new String[] { "Cup", "Capacity" }));
        coffeeSizesTablePane.setPreferredSize(TableSize.SMALL.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;

        super.add(coffeeSizesTablePane, constraints);

        constraints.gridy++;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.coffeeSizeComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoffeeSizeSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

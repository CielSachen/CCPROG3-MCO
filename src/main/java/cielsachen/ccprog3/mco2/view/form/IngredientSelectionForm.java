package cielsachen.ccprog3.mco2.view.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing a form for selecting the new ingredient a storage bin should contain. */
public class IngredientSelectionForm extends JFrame {
    /** The selection of ingredients to select from. */
    public final JComboBox<Ingredient> ingredientComboBox;
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code IngredientSelectionForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param storageBins     The storage bins to use for context. This should exclude the storage bin whose ingredient
     *                        is being replaced.
     * @param ingredients     The ingredients to use.
     */
    public IngredientSelectionForm(Component parentComponent, List<StorageBin> storageBins,
            List<Ingredient> ingredients) {
        super("Storage Bin Selection");

        this.ingredientComboBox = new JComboBox<Ingredient>(ingredients.toArray(Ingredient[]::new));

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("What item should this storage bin contain instead?"), constraints);

        var storageBinsTablePane = new JScrollPane(new JTable(
                storageBins.stream().map((sb) -> new String[] { Integer.toString(sb.id), sb.getIngredient().name })
                        .toArray(String[][]::new),
                new String[] { "ID", "Ingredient" }));
        storageBinsTablePane.setPreferredSize(TableSize.SMALL.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;

        super.add(storageBinsTablePane, constraints);

        constraints.gridy++;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.ingredientComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IngredientSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

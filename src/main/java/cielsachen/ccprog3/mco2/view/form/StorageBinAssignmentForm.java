package cielsachen.ccprog3.mco2.view.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;

/** Represents the window containing the form for setting the initial ingredients of all of a truck’s storage bins. */
public class StorageBinAssignmentForm extends JFrame {
    /** The selections, for each storage bin, of ingredients to select from. */
    public final List<JComboBox<Ingredient>> ingredientComboBoxes = new ArrayList<JComboBox<Ingredient>>();
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code StorageBinAssignmentForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param truck           The truck to use.
     */
    public StorageBinAssignmentForm(Component parentComponent, Truck truck) {
        super("Storage Bin Assignment");

        for (int i = 0; i < (truck.isSpecial ? StorageBin.SPECIAL_TRUCK_COUNT : StorageBin.STANDARD_TRUCK_COUNT); i++) {
            ingredientComboBoxes.add(new JComboBox<Ingredient>(
                    i < StorageBin.STANDARD_TRUCK_COUNT ? Ingredient.regularValues().toArray(Ingredient[]::new)
                            : Ingredient.specialValues().toArray(Ingredient[]::new)));
        }

        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = -1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);

        for (int i = 0; i < this.ingredientComboBoxes.size(); i++) {
            constraints.gridy++;
            constraints.insets.bottom = 2;

            super.add(new JLabel("Storage  Bin #" + i), constraints);

            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom;
            constraints.insets.bottom = 4;

            super.add(this.ingredientComboBoxes.get(i), constraints);
        }

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StorageBinAssignmentForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

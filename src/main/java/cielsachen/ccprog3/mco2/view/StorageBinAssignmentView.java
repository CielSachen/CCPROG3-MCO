package cielsachen.ccprog3.mco2.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cielsachen.ccprog3.mco1.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Truck;

public class StorageBinAssignmentView extends JFrame {
    public final List<JComboBox<Ingredient>> ingredientComboBoxes = new ArrayList<JComboBox<Ingredient>>();
    public final JButton submitButton = new JButton("Submit");

    public StorageBinAssignmentView(Truck truck, boolean isUpdating) {
        super("Update Storage Bins");

        for (int i = 0; i < (truck.isSpecial ? StorageBin.SPECIAL_TRUCK_COUNT
                : StorageBin.STANDARD_TRUCK_COUNT); i++) {
            ingredientComboBoxes.add(new JComboBox<Ingredient>(new Vector<Ingredient>(
                    i < StorageBin.STANDARD_TRUCK_COUNT ? Ingredient.regularValues() : Ingredient.specialValues())));
        }

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(null);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridx = 0;
        constraints.gridy = -1;

        for (int i = 0; i < this.ingredientComboBoxes.size(); i++) {
            JComboBox<Ingredient> comboBox = this.ingredientComboBoxes.get(i);

            var comboBoxLabel = new JLabel("Storage  Bin #" + i);

            constraints.gridy++;
            constraints.insets.bottom = 2;

            super.add(comboBoxLabel, constraints);

            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom;
            constraints.insets.bottom = 4;

            super.add(comboBox, constraints);
        }

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StorageBinAssignmentView.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

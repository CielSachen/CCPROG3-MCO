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

import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing the form for selecting a storage bin. */
public class StorageBinSelectionForm extends JFrame {
    /** The selection of storage bins to select from. */
    public final JComboBox<StorageBin> storageBinComboBox;
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code StorageBinSelectionForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param storageBins     The storage bins to use.
     */
    public StorageBinSelectionForm(Component parentComponent, List<StorageBin> storageBins) {
        super("Storage Bin Selection");

        this.storageBinComboBox = new JComboBox<StorageBin>(storageBins.toArray(StorageBin[]::new));

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Which storage bin would you like to update?"), constraints);

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

        super.add(this.storageBinComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StorageBinSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

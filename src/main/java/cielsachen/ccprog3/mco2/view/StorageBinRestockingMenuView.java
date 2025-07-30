package cielsachen.ccprog3.mco2.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/** Represents the window containing the storage bin restocking menu. */
public class StorageBinRestockingMenuView extends JFrame {
    /** The button to click to restock a storage bin. */
    public final JButton restockButton = new JButton("Restock");
    /** The button to click to empty a storage bin. */
    public final JButton emptyButton = new JButton("Empty");
    /** The button to click to change the ingredient a storage bin contains. */
    public final JButton changeIngredientButton = new JButton("Change Ingredients");

    /**
     * Creates and returns a new {@code StorageBinRestockingForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     */
    public StorageBinRestockingMenuView(Component parentComponent) {
        super("Storage Bin Restocking");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var subtitleLabel = new JLabel("What would you like to do to the storage bin?");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 25));

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);

        super.add(subtitleLabel, constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom = 5;
        constraints.ipadx = constraints.ipady = 10;

        super.add(this.restockButton, constraints);

        constraints.gridy++;

        super.add(this.emptyButton, constraints);

        constraints.gridy++;
        constraints.insets.bottom = 20;

        super.add(this.changeIngredientButton, constraints);

        super.pack();
        super.setVisible(true);
    }
}

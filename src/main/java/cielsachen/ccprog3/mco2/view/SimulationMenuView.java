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

/** Represents the window containing the simulation menu. */
public class SimulationMenuView extends JFrame {
    /** The button to click to simulate the sale of coffee. */
    public final JButton coffeeSaleButton = new JButton("Sale and Preparation of the Coffee Drink");
    /** The button to click to view information about a truck. */
    public final JButton viewTruckButton = new JButton("View Truck Information");
    /** The button to click to restock a truck’s storage bins. */
    public final JButton restockButton = new JButton("Restocking of Storage Bins");
    /** The button to click to perform maintenance on a truck. */
    public final JButton maintenanceButton = new JButton("Maintenance");

    /**
     * Creates and returns a new {@code SimulationForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     */
    public SimulationMenuView(Component parentComponent) {
        super("Feature Simulator");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var subtitleLabel = new JLabel("Which feature would you like to simulate?");
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

        super.add(this.coffeeSaleButton, constraints);

        constraints.gridy++;

        super.add(this.viewTruckButton, constraints);

        constraints.gridy++;

        super.add(this.restockButton, constraints);

        constraints.gridy++;
        constraints.insets.bottom = 20;

        super.add(this.maintenanceButton, constraints);

        super.pack();
        super.setVisible(true);
    }
}

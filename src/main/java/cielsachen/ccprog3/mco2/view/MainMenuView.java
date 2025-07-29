package cielsachen.ccprog3.mco2.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainMenuView extends JFrame {
    public final JButton createTruckButton = new JButton("Create a Coffee Truck");
    public final JButton simulateButton = new JButton("Simulate");
    public final JButton dashboardButton = new JButton("Dashboard");

    public MainMenuView() {
        super("Coffee Truck Manager Simulator: MCO2");

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        super.setLocationRelativeTo(null);

        super.setLayout(new GridBagLayout());

        var subtitleLabel = new JLabel("What Would You Like to Do?");
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

        super.add(this.createTruckButton, constraints);

        constraints.gridy++;

        super.add(this.simulateButton, constraints);

        constraints.gridy++;
        constraints.insets.bottom = 20;

        super.add(this.dashboardButton, constraints);

        super.pack();
    }
}

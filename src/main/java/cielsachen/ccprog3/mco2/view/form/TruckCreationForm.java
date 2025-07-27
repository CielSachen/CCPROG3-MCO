package cielsachen.ccprog3.mco2.view.form;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TruckCreationForm extends JFrame {
    public final JTextField locationField = new JTextField();
    public final JCheckBox isSpecialCheckBox = new JCheckBox("Make a Special Truck?");
    public final JButton submitButton = new JButton("Submit");

    public TruckCreationForm() {
        super("Create a New Truck");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(null);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridx = constraints.gridy = 0;

        var locationFieldLabel = new JLabel("Where will this truck be located?");

        constraints.insets.bottom = 2;

        super.add(locationFieldLabel, constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.locationField, constraints);

        constraints.gridy++;

        super.add(this.isSpecialCheckBox, constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TruckCreationForm.this.locationField.getText().isEmpty()) {
                    return;
                }

                TruckCreationForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

package cielsachen.ccprog3.mco2.view.form;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import cielsachen.ccprog3.mco2.model.Truck;

public class TruckSelectionForm extends JFrame {
    public final JList<Truck> truckComboBox;
    public final JButton submitButton = new JButton("Submit");

    public TruckSelectionForm(JFrame parentFrame, List<Truck> trucks) {
        super("Truck Selection");

        this.truckComboBox = new JList<Truck>(trucks.toArray(Truck[]::new));

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please select a truck by its location…"), constraints);

        constraints.gridy++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.truckComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TruckSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

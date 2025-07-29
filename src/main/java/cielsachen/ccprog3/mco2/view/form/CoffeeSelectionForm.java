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

import cielsachen.ccprog3.mco2.model.coffee.Coffee;

public class CoffeeSelectionForm extends JFrame {
    public final JList<Coffee> coffeeComboBox;
    public final JButton submitButton = new JButton("Submit");

    public CoffeeSelectionForm(JFrame parentFrame, List<Coffee> coffees) {
        super("Coffee Selection");

        this.coffeeComboBox = new JList<Coffee>(coffees.toArray(Coffee[]::new));

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please select a coffee to brew…"), constraints);

        constraints.gridy++;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.coffeeComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoffeeSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

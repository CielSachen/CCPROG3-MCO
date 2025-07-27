package cielsachen.ccprog3.mco2.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.validator.FloatDocumentFilter;

public class PriceConfigurationView extends JFrame {
    public final List<JTextField> priceFields = new ArrayList<JTextField>();
    public final JButton submitButton = new JButton("Submit");

    public PriceConfigurationView(Coffee[] coffees) {
        super("Configure Coffee Prices");

        for (int i = 0; i < coffees.length + 2; i++) {
            var priceField = new JTextField();
            var priceFieldDoc = (PlainDocument) priceField.getDocument();
            priceFieldDoc.setDocumentFilter(new FloatDocumentFilter());

            this.priceFields.add(priceField);
        }

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(null);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridx = 0;
        constraints.gridy = -1;

        int i;

        for (i = 0; i < coffees.length; i++) {
            Coffee coffee = coffees[i];

            var coffeeLabel = new JLabel(coffee.name);

            constraints.gridy++;
            constraints.insets.bottom = 2;

            super.add(coffeeLabel, constraints);

            constraints.gridy++;
            constraints.insets.top = constraints.insets.bottom;
            constraints.insets.bottom = 4;

            super.add(this.priceFields.get(i), constraints);
        }

        var espressoShotLabel = new JLabel("Extra Espresso Shot");
        var syrupLabel = new JLabel("Add-on Syrup");

        int maxGridY = constraints.gridy + 2;
        constraints.gridx++;
        constraints.gridy = 0;

        this.addLabeledComponent(espressoShotLabel, this.priceFields.get(i), constraints);
        this.addLabeledComponent(syrupLabel, this.priceFields.get(++i), constraints);

        constraints.gridy = maxGridY + 1;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PriceConfigurationView.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }

    private void addLabeledComponent(JLabel label, Component component, GridBagConstraints constraints) {
        constraints.gridy++;
        constraints.insets.bottom = 2;

        super.add(label, constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 4;

        super.add(component, constraints);
    }
}

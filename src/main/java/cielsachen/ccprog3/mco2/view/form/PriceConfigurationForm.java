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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.validator.FloatDocumentFilter;

/** Represents the window containing a form for setting the prices of all coffees and add-ons. */
public class PriceConfigurationForm extends JFrame {
    /** The fields to input the prices of each coffee and add-on. */
    public final List<JTextField> priceFields = new ArrayList<JTextField>();
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code PriceConfigurationForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param coffees         The coffees to use.
     */
    public PriceConfigurationForm(Component parentComponent, Coffee[] coffees) {
        super("Coffee Prices Configuration");

        for (int i = 0; i < coffees.length + 2; i++) {
            var priceField = new JTextField();
            var priceFieldDoc = (PlainDocument) priceField.getDocument();
            priceFieldDoc.setDocumentFilter(new FloatDocumentFilter());

            this.priceFields.add(priceField);
        }

        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = -1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 20, 20);

        int i;

        for (i = 0; i < coffees.length; i++) {
            this.addLabeledComponent(new JLabel(coffees[i].name), this.priceFields.get(i), constraints);
        }

        constraints.gridx++;
        int maxGridY = constraints.gridy + 2;
        constraints.gridy = 0;

        this.addLabeledComponent(new JLabel("Extra Espresso Shot"), this.priceFields.get(i), constraints);
        this.addLabeledComponent(new JLabel("Add-on Syrup"), this.priceFields.get(i + 1), constraints);

        constraints.gridy = maxGridY + 1;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PriceConfigurationForm.this.priceFields.stream().anyMatch((field) -> field.getText().isEmpty())) {
                    return;
                }

                PriceConfigurationForm.super.dispose();
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

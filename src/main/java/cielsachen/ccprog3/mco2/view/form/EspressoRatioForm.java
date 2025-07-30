package cielsachen.ccprog3.mco2.view.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import cielsachen.ccprog3.mco2.validator.IntegerDocumentFilter;

/** Represents the window containing a form for inputting the values of the custom espresso ratio. */
public class EspressoRatioForm extends JFrame {
    /** The field to input the ratio of water (?:1). */
    public final JTextField waterRatioField = new JTextField();
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code EspressoRatioForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     */
    public EspressoRatioForm(Component parentComponent) {
        super("Espresso Ratio");

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please input the ratio of water to coffee beans…"), constraints);

        var waterRatioFieldDoc = (PlainDocument) this.waterRatioField.getDocument();
        waterRatioFieldDoc.setDocumentFilter(new IntegerDocumentFilter());

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.waterRatioField, constraints);

        constraints.gridy++;
        constraints.insets.top = constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EspressoRatioForm.this.waterRatioField.getText().isEmpty()) {
                    return;
                }

                EspressoRatioForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

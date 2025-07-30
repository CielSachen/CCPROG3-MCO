package cielsachen.ccprog3.mco2.view.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.view.component.TableSize;

/** Represents the window containing a form for selecting an espresso ratio. */
public class EspressoRatioSelectionForm extends JFrame {
    /** The selection of espresso ratios to select from. */
    public final JComboBox<EspressoRatio> coffeeSizeComboBox;
    /** The button to click to submit the selection. */
    public final JButton submitButton = new JButton("Submit");

    /**
     * Creates and returns a new {@code EspressoRatioSelectionForm} object instance.
     *
     * @param parentComponent The parent component of the window.
     * @param espressoRatios  The espresso ratios to use.
     */
    public EspressoRatioSelectionForm(Component parentComponent, EspressoRatio[] espressoRatios) {
        super("Espresso Ratio Selection");

        this.coffeeSizeComboBox = new JComboBox<EspressoRatio>(espressoRatios);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentComponent);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please select the size of the coffee to brew…"), constraints);

        var espressoRatiosTablePane = new JScrollPane(new JTable(
                Arrays.stream(espressoRatios).map((er) -> new String[] { er.name, er.toString() })
                        .toArray(String[][]::new),
                new String[] { "Name", "Ratio" }));
        espressoRatiosTablePane.setPreferredSize(TableSize.SMALL.dimension);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;

        super.add(espressoRatiosTablePane, constraints);

        constraints.gridy++;
        constraints.weighty = 0.5;
        constraints.insets.top = constraints.insets.bottom;

        super.add(this.coffeeSizeComboBox, constraints);

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.top = constraints.insets.bottom;
        constraints.insets.bottom = 20;

        super.add(this.submitButton, constraints);

        this.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EspressoRatioSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

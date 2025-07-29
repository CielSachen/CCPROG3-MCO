package cielsachen.ccprog3.mco2.view.form;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;

public class CoffeeSizeSelectionForm extends JFrame {
    public final JList<CoffeeSize> coffeeSizeComboBox;
    public final JButton submitButton = new JButton("Submit");

    public CoffeeSizeSelectionForm(JFrame parentFrame, CoffeeSize[] coffeeSizes) {
        super("Coffee Selection");

        this.coffeeSizeComboBox = new JList<CoffeeSize>(coffeeSizes);

        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        super.setLocationRelativeTo(parentFrame);

        super.setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 2, 20);

        super.add(new JLabel("Please select the size of the coffee to brew…"), constraints);

        var coffeeSizesTablePanel = new JScrollPane(new JTable(
                Arrays.stream(coffeeSizes).map((cf) -> new String[] { cf.name, cf.toString() })
                        .toArray(String[][]::new),
                new String[] { "Cup", "Capacity" }));
        coffeeSizesTablePanel.setPreferredSize(new Dimension(150, 68));

        constraints.gridy++;
        constraints.weightx = constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.top = constraints.insets.bottom;

        super.add(coffeeSizesTablePanel, constraints);

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
                CoffeeSizeSelectionForm.super.dispose();
            }
        });

        super.pack();
        super.setVisible(true);
    }
}

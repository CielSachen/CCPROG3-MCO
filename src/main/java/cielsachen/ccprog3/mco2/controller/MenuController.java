package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cielsachen.ccprog3.mco2.view.MainMenuView;

public class MenuController {
    private final MainMenuView view;

    public MenuController(TruckController truckController, CoffeeController coffeeController,
            TransactionController transactionController) {
        this.view = new MainMenuView();

        this.view.createTruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                truckController.createTruck();
            }
        });
        this.view.simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });
        this.view.dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();

                truckController.printTrucksInfo();

                System.out.println();

                transactionController.printTransactions();
            }
        });
    }

    public void showMainMenu() {
        this.view.pack();
        this.view.setVisible(true);
    }
}

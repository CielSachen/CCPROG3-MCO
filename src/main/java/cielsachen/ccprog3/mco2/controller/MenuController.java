package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.service.TruckService;
import cielsachen.ccprog3.mco2.view.DashboardView;
import cielsachen.ccprog3.mco2.view.MainMenuView;

public class MenuController {
    private final MainMenuView view;

    public MenuController(TruckController truckController, CoffeeController coffeeController, TruckService truckService,
            StorageBinService storageBinService, TransactionService transactionService) {
        this.view = new MainMenuView();

        this.view.createTruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                truckController.createTruck(MenuController.this.view);
            }
        });
        this.view.simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Truck truck = truckService.getTrucks().get(0);

                if (coffeeController.isCapableOfBrewing(truck)) {
                    coffeeController.prepareCoffee(truck);
                }
            }

        });
        this.view.dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new DashboardView(
                        MenuController.this.view,
                        truckService.getTrucks().size(),
                        truckService.getSpecialTrucks().size(),
                        storageBinService.getIngredientCapacities(),
                        transactionService.getTransactions());
            }
        });
    }

    public void showMainMenu() {
        this.view.setVisible(true);
    }
}

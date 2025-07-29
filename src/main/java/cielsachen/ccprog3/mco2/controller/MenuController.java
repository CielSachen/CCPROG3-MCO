package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.service.TruckService;
import cielsachen.ccprog3.mco2.view.DashboardView;
import cielsachen.ccprog3.mco2.view.MainMenuView;
import cielsachen.ccprog3.mco2.view.TruckView;
import cielsachen.ccprog3.mco2.view.component.Modal;
import cielsachen.ccprog3.mco2.view.form.SimulationForm;
import cielsachen.ccprog3.mco2.view.form.TruckSelectionForm;

public class MenuController {
    private final MainMenuView view;

    public MenuController(TruckController truckController, CoffeeController coffeeController, TruckService truckService,
            StorageBinService storageBinService, CoffeeService coffeeService, TransactionService transactionService) {
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
                var truckSelectionForm = new TruckSelectionForm(MenuController.this.view, truckService.getTrucks());

                truckSelectionForm.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Truck chosenTruck = truckSelectionForm.truckComboBox.getSelectedValue();

                        var simulationForm = new SimulationForm(MenuController.this.view);

                        simulationForm.coffeeSaleButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!coffeeService.isCapableOfBrewing(chosenTruck)) {
                                    Modal.showError(
                                            "The selected truck doesn’t have the ingredients to brew coffee!",
                                            "Missing Ingredients");

                                    return;
                                }

                                coffeeController.prepareCoffee(simulationForm, chosenTruck);
                            }
                        });
                        simulationForm.viewTruckButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new TruckView(
                                        simulationForm,
                                        chosenTruck,
                                        storageBinService.getStorageBinsByTruck(chosenTruck),
                                        coffeeService.getCoffeesByTruck(chosenTruck),
                                        coffeeService.espresso,
                                        coffeeService.syrup);
                            }
                        });
                        simulationForm.restockButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                            }
                        });
                        simulationForm.maintenanceButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                            }
                        });
                    }
                });
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

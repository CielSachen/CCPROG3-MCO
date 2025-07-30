package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.service.TruckService;
import cielsachen.ccprog3.mco2.view.DashboardView;
import cielsachen.ccprog3.mco2.view.MainMenuView;
import cielsachen.ccprog3.mco2.view.SimulationMenuView;
import cielsachen.ccprog3.mco2.view.TruckView;
import cielsachen.ccprog3.mco2.view.component.Modal;

/** Represents a controller for handling the main menus. */
public class MenuController {
    private final MainMenuView view;

    /**
     * Creates and returns a new {@code MenuController} object instance.
     *
     * @param truckController    The truck controller to use.
     * @param coffeeController   The coffee controller to use.
     * @param truckService       The truck service to use.
     * @param storageBinService  The storage bin service to use.
     * @param coffeeService      The coffee service to use.
     * @param transactionService The transaction service to use.
     */
    public MenuController(TruckController truckController, CoffeeController coffeeController, TruckService truckService,
            StorageBinService storageBinService, CoffeeService coffeeService, TransactionService transactionService) {
        this.view = new MainMenuView();

        this.view.createTruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                truckController.createTruck(MenuController.this.view);
            }
        });
        this.view.simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Truck> trucks = truckService.getTrucks();

                if (trucks.size() == 0) {
                    Modal.showErrorDialog(MenuController.this.view, "You have not yet created any trucks!",
                            "Truck Selection");

                    return;
                }

                Truck selectedTruck = Modal.showSelectionDialog(MenuController.this.view,
                        "Please select a truck by its location…", "Truck Selection", trucks, trucks.getLast());

                if (selectedTruck == null) {
                    return;
                }

                var simulationForm = new SimulationMenuView(MenuController.this.view);

                simulationForm.coffeeSaleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (!coffeeService.isCapableOfBrewing(selectedTruck)) {
                            Modal.showErrorDialog(simulationForm,
                                    "The selected truck doesn’t have the ingredients to brew coffee!",
                                    "Missing Ingredients");

                            return;
                        }

                        coffeeController.prepareCoffee(simulationForm, selectedTruck);
                    }
                });
                simulationForm.viewTruckButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new TruckView(simulationForm, selectedTruck,
                                storageBinService.getStorageBinsByTruck(selectedTruck),
                                coffeeService.getCoffeesByTruck(selectedTruck), coffeeService.espresso,
                                coffeeService.syrup, transactionService.getTransactionsByTruck(selectedTruck));
                    }
                });
                simulationForm.restockButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        truckController.restockStorageBins(simulationForm, selectedTruck);
                    }
                });
                simulationForm.maintenanceButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (Modal.showConfirmDialog(simulationForm,
                                "Do you want to relocate the truck (currently located at: "
                                        + selectedTruck.getLocation() + ")?",
                                "Truck Relocation") == JOptionPane.YES_OPTION) {
                            truckController.relocateTruck(simulationForm, selectedTruck);
                        }

                        if (Modal.showConfirmDialog(simulationForm,
                                "Do you want to update the coffee prices (of all trucks)?",
                                "Coffee Prices Configuration") == JOptionPane.YES_OPTION) {
                            coffeeController.updatePrices(simulationForm);
                        }
                    }
                });
            }
        });
        this.view.dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DashboardView(MenuController.this.view, truckService.getTrucks().size(),
                        truckService.getSpecialTrucks().size(), storageBinService.getIngredientCapacities(),
                        transactionService.getTransactions());
            }
        });
    }

    /** Shows the main menu application GUI. */
    public void showMainMenu() {
        this.view.setVisible(true);
    }
}

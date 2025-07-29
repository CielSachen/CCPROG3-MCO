package cielsachen.ccprog3.mco2.controller;

import java.util.List;

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

public class MenuController {
    private final MainMenuView view;

    public MenuController(TruckController truckController, CoffeeController coffeeController, TruckService truckService,
            StorageBinService storageBinService, CoffeeService coffeeService, TransactionService transactionService) {
        this.view = new MainMenuView();

        this.view.createTruckButton.addActionListener((e) -> truckController.createTruck(MenuController.this.view));
        this.view.simulateButton.addActionListener((evt) -> {
            List<Truck> trucks = truckService.getTrucks();

            if (trucks.size() == 0) {
                Modal.showErrorDialog(MenuController.this.view, "You have not yet created any trucks!", "No Trucks");

                return;
            }

            Truck selectedTruck = Modal.showSelectionDialog(
                    MenuController.this.view,
                    "Please select a truck by its location…",
                    "Truck Selection",
                    trucks,
                    trucks.getLast());

            var simulationForm = new SimulationForm(MenuController.this.view);

            simulationForm.coffeeSaleButton.addActionListener((e) -> {
                if (!coffeeService.isCapableOfBrewing(selectedTruck)) {
                    Modal.showErrorDialog(MenuController.this.view,
                            "The selected truck doesn’t have the ingredients to brew coffee!",
                            "Missing Ingredients");

                    return;
                }

                coffeeController.prepareCoffee(simulationForm, selectedTruck);
            });
            simulationForm.viewTruckButton.addActionListener((e) -> {
                new TruckView(
                        simulationForm,
                        selectedTruck,
                        storageBinService.getStorageBinsByTruck(selectedTruck),
                        coffeeService.getCoffeesByTruck(selectedTruck),
                        coffeeService.espresso,
                        coffeeService.syrup);
            });
            simulationForm.restockButton.addActionListener((e) -> {
            });
            simulationForm.maintenanceButton.addActionListener((e) -> {
            });
        });
        this.view.dashboardButton.addActionListener((e) -> new DashboardView(
                MenuController.this.view,
                truckService.getTrucks().size(),
                truckService.getSpecialTrucks().size(),
                storageBinService.getIngredientCapacities(),
                transactionService.getTransactions()));
    }

    public void showMainMenu() {
        this.view.setVisible(true);
    }
}

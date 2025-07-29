package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cielsachen.ccprog3.mco2.exception.InsufficientCapacityException;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TransactionService;
import cielsachen.ccprog3.mco2.view.BrewCompletionView;
import cielsachen.ccprog3.mco2.view.component.Modal;
import cielsachen.ccprog3.mco2.view.form.CoffeeSizeSelectionForm;
import cielsachen.ccprog3.mco2.view.form.EspressoRatioForm;
import cielsachen.ccprog3.mco2.view.form.EspressoRatioSelectionForm;
import cielsachen.ccprog3.mco2.view.form.PriceConfigurationForm;

/** Represents a controller for interacting with coffees. */
public class CoffeeController {
    private final CoffeeService service;
    private final StorageBinService storageBinService;
    private final TransactionService transactionService;

    private int extraEspressoShotsCnt;
    private float additionalCost;

    /**
     * Creates a new {@code CoffeeController} object instance.
     *
     * @param service The coffee service to use.
     */
    public CoffeeController(CoffeeService service, StorageBinService storageBinService,
            TransactionService transactionService) {
        this.service = service;
        this.storageBinService = storageBinService;
        this.transactionService = transactionService;
    }

    /** Changes the prices of all coffees and add-ons. */
    public void updatePrices(JFrame parentFrame) {
        Coffee[] coffees = this.service.getCoffees();

        var priceConfigurationView = new PriceConfigurationForm(parentFrame, coffees);

        priceConfigurationView.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Float> prices;

                try {
                    prices = priceConfigurationView.priceFields.stream()
                            .map((field) -> Float.parseFloat(field.getText())).toList();
                } catch (NumberFormatException e) {
                    Modal.showErrorDialog(priceConfigurationView, "All fields must be filled!", "Missing Fields");

                    return;
                }

                int i;

                for (i = 0; i < coffees.length; i++) {
                    coffees[i].setPrice(prices.get(i));
                }

                CoffeeController.this.service.espresso.setPrice(prices.get(i));
                CoffeeController.this.service.syrup.setPrice(prices.get(i + 1));
            }
        });
    }

    /**
     * Prepares a coffee in a truck. This will decrease the capacities of relevant storage bins and create a new
     * transaction.
     *
     * @param truck The truck to prepare the coffee in.
     */
    public void prepareCoffee(JFrame parentFrame, Truck truck) {
        List<Coffee> coffees = this.service.getCoffeesByTruck(truck);

        var selectedCoffee = (Coffee) Modal.showSelectionDialog(parentFrame, "Please select a coffee to brew…",
                "Coffee Selection", coffees, coffees.getFirst());

        var coffeeSizeSelectionForm = new CoffeeSizeSelectionForm(parentFrame, Stream.of(CoffeeSize.values())
                .filter((cs) -> !this.storageBinService.getStorageBinsByTruck(truck, cs.cup).isEmpty())
                .toArray(CoffeeSize[]::new));

        coffeeSizeSelectionForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                var selectedSize = (CoffeeSize) coffeeSizeSelectionForm.coffeeSizeComboBox.getSelectedItem();

                if (!truck.isSpecial) {
                    CoffeeController.this.brewCoffee(parentFrame, truck, selectedCoffee, selectedSize,
                            EspressoRatio.STANDARD);

                    return;
                }

                var espressoRatioSelectionForm = new EspressoRatioSelectionForm(parentFrame,
                        truck.isSpecial ? EspressoRatio.values() : EspressoRatio.regularValues());

                espressoRatioSelectionForm.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        var selectedRatio = (EspressoRatio) espressoRatioSelectionForm.coffeeSizeComboBox
                                .getSelectedItem();

                        if (selectedRatio.equals(EspressoRatio.CUSTOM)) {
                            var espressoRatioForm = new EspressoRatioForm(parentFrame);

                            espressoRatioForm.submitButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    int waterRatio;

                                    try {
                                        waterRatio = Integer.parseInt(espressoRatioForm.waterRatioField.getText());
                                    } catch (NumberFormatException e) {
                                        Modal.showErrorDialog(parentFrame, "All fields must be filled!",
                                                "Missing Fields");

                                        return;
                                    }

                                    EspressoRatio.setCustomRatio(waterRatio);

                                    CoffeeController.this.brewCoffee(parentFrame, truck, selectedCoffee, selectedSize,
                                            selectedRatio);
                                };
                            });

                            return;
                        }

                        CoffeeController.this.brewCoffee(parentFrame, truck, selectedCoffee, selectedSize,
                                selectedRatio);
                    }
                });
            }
        });
    }

    private void addEspressoShots(JFrame parentFrame, Truck truck, EspressoRatio ratio,
            Map<Ingredient, Double> amountsByIngredient) {
        try {
            this.service.canBrewEspressoShots(truck, this.extraEspressoShotsCnt, ratio);

            amountsByIngredient.putAll(this.service.brewEspressoShots(truck, this.extraEspressoShotsCnt, ratio));

            this.additionalCost += this.service.espresso.getPrice() * this.extraEspressoShotsCnt;
        } catch (InsufficientCapacityException e) {
            Modal.showErrorDialog(parentFrame,
                    "The selected truck does not have enough " + e.ingredient.name + " to brew the coffee!",
                    "Insufficient Ingredient");
        }
    }

    private void addSyrup(JFrame parentFrame, Truck truck, Map<Ingredient, Double> amountsByIngredient) {
        int isAddingSyrups = Modal.showConfirmDialog(parentFrame, "Would you like to add pumps of syrup?",
                "Add-On Syrup");

        if (isAddingSyrups == JOptionPane.YES_OPTION) {
            List<Ingredient> syrups = this.storageBinService.getStorageBinsByTruck(truck).stream()
                    .map((sb) -> sb.getIngredient()).filter((i) -> i.isSpecial).toList();

            Ingredient selectedSyrup = Modal.showSelectionDialog(parentFrame, "How many extra shots should be added?",
                    "Add-On Syrup", syrups, syrups.getFirst());

            int syrupCnt = 0;

            while (true) {
                String givenCnt = Modal.showInputDialog(parentFrame, "How many pumps should be added?", "Add-On Syrup");

                try {
                    syrupCnt = Integer.parseInt(givenCnt);

                    break;
                } catch (NumberFormatException e) {
                    Modal.showErrorDialog(parentFrame, "Please only input a whole number!", "Invalid Input");
                }
            }

            try {
                this.service.canAddSyrup(truck, selectedSyrup, syrupCnt);

                amountsByIngredient.putAll(this.service.addSyrup(truck, selectedSyrup, syrupCnt));

                this.additionalCost += this.service.syrup.getPrice() * syrupCnt;
            } catch (InsufficientCapacityException e) {
                Modal.showErrorDialog(parentFrame,
                        "The selected truck does not have enough " + e.ingredient.name + " to brew the coffee!",
                        "Insufficient Ingredient");
            }
        }
    }

    private void finishBrewing(JFrame parentFrame, Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio,
            Map<Ingredient, Double> amountsByIngredient) {
        float totalCost = coffee.getPrice(size) + this.additionalCost;

        new BrewCompletionView(parentFrame, coffee, ratio, size, this.extraEspressoShotsCnt, amountsByIngredient,
                totalCost);

        this.transactionService.addTransaction(
                new Transaction(coffee.name, size, totalCost, truck, this.extraEspressoShotsCnt, amountsByIngredient));
    }

    private void brewCoffee(JFrame parentFrame, Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) {
        this.extraEspressoShotsCnt = 0;
        this.additionalCost = 0;

        try {
            this.service.canBrewCoffee(truck, coffee, size, ratio);

            Map<Ingredient, Double> amountsByIngredient = new LinkedHashMap<Ingredient, Double>(
                    this.service.brewCoffee(truck, coffee, size, ratio));

            if (!truck.isSpecial) {
                this.finishBrewing(parentFrame, truck, coffee, size, ratio, amountsByIngredient);

                return;
            }

            int isAddingEspressoShots = Modal.showConfirmDialog(parentFrame,
                    "Would you like to add extra shots of espresso?", "Extra Espresso Shots");

            if (isAddingEspressoShots == JOptionPane.YES_OPTION) {
                while (true) {
                    String givenCnt = Modal.showInputDialog(parentFrame, "How many extra shots should be added?",
                            "Extra Espresso Shots");

                    try {
                        this.extraEspressoShotsCnt = Integer.parseInt(givenCnt);

                        break;
                    } catch (NumberFormatException e) {
                        Modal.showErrorDialog(parentFrame, "Please only input a whole number!", "Invalid Input");
                    }
                }

                var espressoRatioSelectionForm = new EspressoRatioSelectionForm(parentFrame, EspressoRatio.values());

                espressoRatioSelectionForm.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        var selectedRatio = (EspressoRatio) espressoRatioSelectionForm.coffeeSizeComboBox
                                .getSelectedItem();

                        if (selectedRatio.equals(EspressoRatio.CUSTOM)) {
                            var espressoRatioForm = new EspressoRatioForm(parentFrame);

                            espressoRatioForm.submitButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    int waterRatio;

                                    try {
                                        waterRatio = Integer.parseInt(espressoRatioForm.waterRatioField.getText());
                                    } catch (NumberFormatException e) {
                                        Modal.showErrorDialog(parentFrame, "All fields must be filled!",
                                                "Missing Fields");

                                        return;
                                    }

                                    EspressoRatio.setCustomRatio(waterRatio);

                                    CoffeeController.this.addEspressoShots(parentFrame, truck, ratio,
                                            amountsByIngredient);

                                    if (CoffeeController.this.storageBinService.truckHasSyrups(truck)) {
                                        CoffeeController.this.addSyrup(parentFrame, truck, amountsByIngredient);
                                    }

                                    CoffeeController.this.finishBrewing(parentFrame, truck, coffee, size, ratio,
                                            amountsByIngredient);
                                };
                            });

                            return;
                        }

                        CoffeeController.this.addEspressoShots(parentFrame, truck, ratio, amountsByIngredient);

                        if (CoffeeController.this.storageBinService.truckHasSyrups(truck)) {
                            CoffeeController.this.addSyrup(parentFrame, truck, amountsByIngredient);
                        }

                        CoffeeController.this.finishBrewing(parentFrame, truck, coffee, size, ratio,
                                amountsByIngredient);
                    }
                });

                return;
            }

            this.addSyrup(parentFrame, truck, amountsByIngredient);

            this.finishBrewing(parentFrame, truck, coffee, size, ratio, amountsByIngredient);
        } catch (InsufficientCapacityException e) {
            Modal.showErrorDialog(parentFrame,
                    "The selected truck does not have enough " + e.ingredient.name + " to brew the coffee!",
                    "Insufficient Ingredient");
        }
    }
}

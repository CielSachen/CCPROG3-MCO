package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cielsachen.ccprog3.mco2.exception.InsufficientCapacityException;
import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;
import cielsachen.ccprog3.mco2.model.coffee.EspressoRatio;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.view.component.Modal;
import cielsachen.ccprog3.mco2.view.form.CoffeeSizeSelectionForm;
import cielsachen.ccprog3.mco2.view.form.EspressoRatioForm;
import cielsachen.ccprog3.mco2.view.form.EspressoRatioSelectionForm;
import cielsachen.ccprog3.mco2.view.form.PriceConfigurationForm;

/** Represents a controller for interacting with coffees. */
public class CoffeeController {
    private final CoffeeService service;

    /**
     * Creates a new {@code CoffeeController} object instance.
     *
     * @param service The coffee service to use.
     */
    public CoffeeController(CoffeeService service) {
        this.service = service;
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

        var selectedCoffee = (Coffee) Modal.showInputDialog(
                parentFrame,
                "Please select a coffee to brew…",
                "Coffee Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                coffees.toArray(),
                coffees.getFirst());

        var coffeeSizeSelectionForm = new CoffeeSizeSelectionForm(parentFrame, CoffeeSize.values());

        coffeeSizeSelectionForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                var espressoRatioSelectionForm = new EspressoRatioSelectionForm(
                        coffeeSizeSelectionForm,
                        truck.isSpecial ? EspressoRatio.values() : EspressoRatio.regularValues());

                espressoRatioSelectionForm.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        var selectedSize = (CoffeeSize) coffeeSizeSelectionForm.coffeeSizeComboBox.getSelectedItem();
                        var selectedRatio = (EspressoRatio) espressoRatioSelectionForm.coffeeSizeComboBox
                                .getSelectedItem();

                        if (selectedRatio.equals(EspressoRatio.CUSTOM)) {
                            var espressoRatioForm = new EspressoRatioForm(espressoRatioSelectionForm);

                            espressoRatioForm.submitButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    int waterRatio;

                                    try {
                                        waterRatio = Integer
                                                .parseInt(espressoRatioForm.waterRatioField.getText());
                                    } catch (NumberFormatException e) {
                                        Modal.showErrorDialog(espressoRatioForm, "All fields must be filled!",
                                                "Missing Fields");

                                        return;
                                    }

                                    EspressoRatio.setCustomRatio(waterRatio);
                                };
                            });

                            CoffeeController.this.brewCoffee(
                                    espressoRatioSelectionForm,
                                    truck,
                                    selectedCoffee,
                                    selectedSize,
                                    selectedRatio);

                            return;
                        }

                        CoffeeController.this.brewCoffee(
                                espressoRatioSelectionForm,
                                truck,
                                selectedCoffee,
                                selectedSize,
                                selectedRatio);
                    }
                });
            }
        });

        // try {
        // this.service.canBrewCoffee(truck, selectedCoffee, selectedSize, selectedRatio);

        // Map<Ingredient, Double> ingredients = new LinkedHashMap<Ingredient, Double>(
        // this.service.brewCoffee(truck, selectedCoffee, selectedSize, selectedRatio));

        // int extraEspressoShotsCount = 0;
        // float additionalCost = 0;

        // if (truck.isSpecial) {
        // boolean isAddingEspressoShots = this.input.getBoolean(
        // "Would you like to add extra shots of espresso "
        // + PrintColor.set("(true/false)", PrintColor.RED) + "?",
        // !selectedRatio.equals(EspressoRatio.CUSTOM));

        // if (isAddingEspressoShots) {
        // EspressoRatio chosenShotsRatio = EspressoRatio.STANDARD;

        // if (truck.isSpecial) {
        // while (true) {
        // try {
        // System.out.println();

        // System.out.println("What ratio of espresso would you like?");

        // for (int index = 0; index < espressoRatios.length; index++) {
        // EspressoRatio ratio = espressoRatios[index];

        // System.out.println(" [" + (index + 1) + "] " + ratio.name + " ("
        // + PrintColor.set(ratio.toString(), PrintColor.BRIGHT_CYAN) + ")");
        // }

        // System.out.println();

        // System.out.print(" > ");

        // int selectedRatioIndex = this.scanner.nextInt() - 1;

        // this.scanner.nextLine();

        // if (selectedRatioIndex >= 0 && selectedRatioIndex < espressoRatios.length) {
        // chosenShotsRatio = espressoRatios[selectedRatioIndex];

        // break;
        // }

        // System.out.println();

        // ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
        // } catch (InputMismatchException e) {
        // this.scanner.nextLine();

        // System.out.println();

        // ExceptionMessage.INVALID_CHARACTER_CHOICE.print();
        // }
        // }
        // }

        // if (chosenShotsRatio.equals(EspressoRatio.CUSTOM)) {
        // EspressoRatio.setCustomRatio(
        // this.input.getInteger("What should the ratio of water to coffea beans be "
        // + PrintColor.set("(1 : ?)", PrintColor.YELLOW) + "?", true));
        // }

        // extraEspressoShotsCount = this.input.getInteger("How many extra shots should be added?");

        // try {
        // this.service.canBrewEspressoShots(truck, extraEspressoShotsCount, chosenShotsRatio);

        // ingredients.putAll(this.service.brewEspressoShots(truck, extraEspressoShotsCount,
        // chosenShotsRatio));

        // additionalCost += this.service.espresso.getPrice() * extraEspressoShotsCount;
        // } catch (InsufficientCapacityException e) {
        // System.out.println();

        // System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
        // + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
        // + PrintColor.set("to brew the coffee.", PrintColor.RED));
        // }
        // }

        // boolean isAddingSyrups = true;

        // List<Ingredient> syrups = Ingredient.specialValues();
        // int syrupCount = syrups.size();

        // while (isAddingSyrups) {
        // isAddingSyrups = this.input.getBoolean("Would you like to add pumps of syrup "
        // + PrintColor.set("(true/false)", PrintColor.RED) + "?");

        // if (isAddingSyrups) {
        // Ingredient chosenSyrup = null;

        // while (true) {
        // try {
        // System.out.println();

        // System.out.println("Which syrup would you like to add?");

        // for (int index = 0; index < syrupCount; index++) {
        // Ingredient ingredient = syrups.get(index);

        // if (this.storageBinService.truckHasIngredient(truck, ingredient)) {
        // System.out.println(" [" + (index + 1) + "] " + ingredient.name);
        // }
        // }

        // System.out.println();

        // System.out.print(" > ");

        // int chosenSyrupIndex = this.scanner.nextInt() - 1;

        // this.scanner.nextLine();

        // if (chosenSyrupIndex >= 0 && chosenSyrupIndex < syrupCount) {
        // chosenSyrup = syrups.get(chosenSyrupIndex);

        // if (this.storageBinService.truckHasIngredient(truck, chosenSyrup)) {
        // break;
        // }
        // }

        // System.out.println();

        // ExceptionMessage.INVALID_INTEGER_CHOICE.print();
        // } catch (InputMismatchException e) {
        // this.scanner.nextLine();

        // System.out.println();

        // ExceptionMessage.INVALID_INTEGER_CHOICE.print();
        // }
        // }

        // int amount = this.input.getInteger("How many pumps should be added?", true);

        // try {
        // this.service.canAddSyrup(truck, chosenSyrup, amount);

        // ingredients.putAll(this.service.addSyrup(truck, chosenSyrup, amount));

        // additionalCost += this.service.syrup.getPrice() * amount;
        // } catch (InsufficientCapacityException e) {
        // System.out.println();

        // System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
        // + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
        // + PrintColor.set("to brew the coffee.", PrintColor.RED));
        // }
        // }
        // }
        // }

        // System.out.println();

        // System.out.println(">>> Preparing a " + PrintColor.set(selectedSize.name, PrintColor.YELLOW) + " of "
        // + PrintColor.set(selectedCoffee.name, PrintColor.YELLOW) + " ("
        // + PrintColor.set(selectedSize.toString(), PrintColor.BRIGHT_CYAN) + ") with "
        // + PrintColor.set(Integer.toString(extraEspressoShotsCount), PrintColor.BRIGHT_CYAN)
        // + PrintColor.set(" extra shots of espresso...", PrintColor.YELLOW));
        // System.out.println(">>> Brewing " + PrintColor.set(selectedRatio.name + " Espresso", PrintColor.YELLOW));

        // for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
        // Ingredient ingredient = entry.getKey();

        // System.out.println(">>> Adding " + PrintColor.set(ingredient.name, PrintColor.YELLOW) + " ("
        // + PrintColor.set(String.format("%.2f", entry.getValue()) + " " + ingredient.unitMeasure,
        // PrintColor.BRIGHT_CYAN)
        // + ")...");
        // }

        // System.out.println(">>> The " + selectedCoffee.name + " is done!");

        // System.out.println();

        // float totalCost = selectedCoffee.getPrice() + additionalCost;

        // System.out.println("The " + PrintColor.set(selectedSize.name, PrintColor.YELLOW) + " of "
        // + PrintColor.set(selectedCoffee.name, PrintColor.YELLOW) + " will cost "
        // + PrintColor.set(totalCost + " PHP", PrintColor.BRIGHT_GREEN) + ".");

        // this.transactionService.addTransaction(new Transaction(selectedCoffee.name, selectedSize, totalCost, truck,
        // extraEspressoShotsCount, ingredients));
        // } catch (InsufficientCapacityException e) {
        // System.out.println();

        // System.out.println(PrintColor.set("The truck does not have enough ", PrintColor.RED)
        // + PrintColor.set(e.ingredient.name, PrintColor.YELLOW)
        // + PrintColor.set("to brew the coffee.", PrintColor.RED));
        // }
    }

    private void brewCoffee(JFrame parentFrame, Truck truck, Coffee coffee, CoffeeSize size, EspressoRatio ratio) {
        try {
            this.service.canBrewCoffee(truck, coffee, size, ratio);

            Map<Ingredient, Double> amountsByIngredient = this.service.brewCoffee(truck, coffee, size, ratio);
        } catch (InsufficientCapacityException e) {
            Modal.showErrorDialog(parentFrame,
                    "The selected truck does not have enough " + e.ingredient.name + " to brew the coffee!",
                    "Insufficient Ingredient");
        }
    }
}

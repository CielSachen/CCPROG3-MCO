package cielsachen.ccprog3.mco2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;
import cielsachen.ccprog3.mco2.service.CoffeeService;
import cielsachen.ccprog3.mco2.service.StorageBinService;
import cielsachen.ccprog3.mco2.service.TruckService;
import cielsachen.ccprog3.mco2.view.TruckView;
import cielsachen.ccprog3.mco2.view.component.Modal;
import cielsachen.ccprog3.mco2.view.form.IngredientSelectionForm;
import cielsachen.ccprog3.mco2.view.form.StorageBinAssignmentForm;
import cielsachen.ccprog3.mco2.view.form.StorageBinRestockingForm;
import cielsachen.ccprog3.mco2.view.form.StorageBinSelectionForm;
import cielsachen.ccprog3.mco2.view.form.TruckCreationForm;

/** Represents a controller for handling trucks. */
public class TruckController {
    private final CoffeeController coffeeController;

    private final TruckService service;
    private final StorageBinService storageBinService;
    private final CoffeeService coffeeService;

    /**
     * Creates and returns a new {@code TruckController} object instance.
     *
     * @param coffeeController  The coffee controller to use.
     * @param service           The truck service to use.
     * @param storageBinService The storage bin service to use.
     * @param coffeeService     The coffee service to use.
     */
    public TruckController(CoffeeController coffeeController, TruckService service, StorageBinService storageBinService,
            CoffeeService coffeeService) {
        this.coffeeController = coffeeController;

        this.service = service;
        this.storageBinService = storageBinService;
        this.coffeeService = coffeeService;
    }

    /**
     * Creates a new truck and adds it to the system.
     *
     * @param parentFrame The parent frame of the windows to be shown.
     */
    public void createTruck(JFrame parentFrame) {
        var creationForm = new TruckCreationForm(parentFrame);

        creationForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String givenLocation = creationForm.locationField.getText();

                if (givenLocation.isEmpty()) {
                    Modal.showErrorDialog(parentFrame, "A location must be specified!", "Missing Field");

                    return;
                } else if (TruckController.this.service.isOccupiedLocation(givenLocation)) {
                    Modal.showErrorDialog(parentFrame, "A truck already exists on this location!", "Invalid Input");

                    return;
                }

                boolean isSpecial = creationForm.isSpecialCheckBox.isSelected();

                Truck truck = new Truck(TruckController.this.service.getTrucks().size() + 1, givenLocation, isSpecial);

                TruckController.this.service.addTruck(truck);

                var storageBinAssignmentForm = new StorageBinAssignmentForm(creationForm, truck, false);

                storageBinAssignmentForm.submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < storageBinAssignmentForm.ingredientComboBoxes.size(); i++) {
                            JComboBox<Ingredient> ingredientComboBox = storageBinAssignmentForm.ingredientComboBoxes
                                    .get(i);

                            TruckController.this.storageBinService.addStorageBin(
                                    new StorageBin(i + 1, truck, (Ingredient) ingredientComboBox.getSelectedItem()));
                        }

                        boolean isFirstTruck = !TruckController.this.coffeeService.isPricesSet();

                        if (isFirstTruck || Modal.showConfirmDialog(parentFrame, "Do you want to update the prices?",
                                "Update Prices") == JOptionPane.YES_OPTION) {
                            TruckController.this.coffeeController.updatePrices(storageBinAssignmentForm);
                        }

                        new TruckView(
                                parentFrame,
                                truck,
                                TruckController.this.storageBinService.getStorageBinsByTruck(truck),
                                isFirstTruck ? null : TruckController.this.coffeeService.getCoffeesByTruck(truck),
                                isFirstTruck ? null : TruckController.this.coffeeService.espresso,
                                isFirstTruck ? null : TruckController.this.coffeeService.syrup);
                    }
                });
            }
        });
    }

    /**
     * Moves a truck to a new unoccupied location.
     *
     * @param parentFrame The parent frame of the windows to be shown.
     * @param truck       The truck to move.
     */
    public void relocateTruck(JFrame parentFrame, Truck truck) {
        String newLocation;

        while (true) {
            newLocation = Modal.showInputDialog(parentFrame, "Where will this truck be relocated to?",
                    "Truck Relocation");

            System.out.println();

            if (newLocation == null) {
                return;
            } else if (!this.service.isOccupiedLocation(newLocation)) {
                break;
            }

            Modal.showErrorDialog(parentFrame, "A truck already exists on this location!", "Truck Relocation");
        }

        truck.setLocation(newLocation);

        JOptionPane.showMessageDialog(parentFrame, "Relocated the coffee truck to " + newLocation + "!",
                "Truck Relocation", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Restocks, empties, or changes the ingredient of a truck's storage bin.
     *
     * @param parentFrame The parent frame of the windows to be shown.
     * @param truck       The truck to update a storage bin of.
     */
    public void restockStorageBins(JFrame parentFrame, Truck truck) {
        List<StorageBin> storageBins = new ArrayList<StorageBin>(this.storageBinService.getStorageBinsByTruck(truck));

        var storageBinSelectionForm = new StorageBinSelectionForm(parentFrame, storageBins);

        storageBinSelectionForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                var selectedStorageBin = (StorageBin) storageBinSelectionForm.storageBinComboBox.getSelectedItem();

                var storageBinRestockingForm = new StorageBinRestockingForm(parentFrame);

                storageBinRestockingForm.restockButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        double currCapacity = selectedStorageBin.getCapacity();
                        Ingredient currIngredient = selectedStorageBin.getIngredient();

                        if (currCapacity == currIngredient.maximumCapacity) {
                            Modal.showErrorDialog(storageBinRestockingForm, "The storage bin is already full!",
                                    "Storage Bin Restocking");

                            return;
                        }

                        double additionalCapacity = 0;

                        while (true) {
                            String givenNum = Modal.showInputDialog(parentFrame,
                                    "How much " + currIngredient.name + " should be restocked?", "Add-On Syrup");

                            if (givenNum == null) {
                                return;
                            }

                            try {
                                additionalCapacity = Double.parseDouble(givenNum);

                                break;
                            } catch (NumberFormatException e) {
                                Modal.showErrorDialog(parentFrame, "Please only input a floating point number!",
                                        "Invalid Input");
                            }
                        }

                        if (additionalCapacity > 0) {
                            if (currCapacity + additionalCapacity > currIngredient.maximumCapacity) {
                                selectedStorageBin.decreaseCapacity(currCapacity);
                                selectedStorageBin.increaseCapacity(currIngredient.maximumCapacity);
                            } else {
                                selectedStorageBin.increaseCapacity(additionalCapacity);
                            }

                            JOptionPane.showMessageDialog(storageBinRestockingForm,
                                    "The storage bin has been restocked!", "Storage Bin Restocking",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                });
                storageBinRestockingForm.emptyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        double currCapacity = selectedStorageBin.getCapacity();

                        if (currCapacity == 0) {
                            Modal.showErrorDialog(storageBinRestockingForm, "The storage bin is already empty!",
                                    "Storage Bin Restocking");

                            return;
                        }

                        selectedStorageBin.decreaseCapacity(currCapacity);

                        JOptionPane.showMessageDialog(storageBinRestockingForm, "The storage bin has been emptied!",
                                "Storage Bin Restocking", JOptionPane.PLAIN_MESSAGE);
                    }
                });
                storageBinRestockingForm.changeIngredientButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        List<Ingredient> ingredients = new ArrayList<Ingredient>(
                                selectedStorageBin.id > StorageBin.STANDARD_TRUCK_COUNT ? Ingredient.specialValues()
                                        : Ingredient.regularValues());

                        storageBins.remove(selectedStorageBin);
                        ingredients.remove(selectedStorageBin.getIngredient());

                        var ingredientSelectionForm = new IngredientSelectionForm(parentFrame, storageBins,
                                ingredients);

                        ingredientSelectionForm.submitButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                double currCapacity = selectedStorageBin.getCapacity();

                                if (currCapacity > 0) {
                                    selectedStorageBin.decreaseCapacity(currCapacity);
                                }

                                var selectedIngredient = (Ingredient) ingredientSelectionForm.ingredientComboBox
                                        .getSelectedItem();

                                selectedStorageBin.setIngredient(selectedIngredient);

                                selectedStorageBin.increaseCapacity(selectedIngredient.maximumCapacity);
                            }
                        });
                    }
                });
            }
        });
    }
}

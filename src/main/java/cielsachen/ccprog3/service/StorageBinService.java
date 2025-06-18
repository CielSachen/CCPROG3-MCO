package cielsachen.ccprog3.service;

import java.util.List;

import cielsachen.ccprog3.model.Ingredient;
import cielsachen.ccprog3.model.StorageBin;
import cielsachen.ccprog3.model.Truck;

public class StorageBinService {
    private final List<StorageBin> storageBins;

    public StorageBinService(List<StorageBin> storageBins) {
        this.storageBins = storageBins;
    }

    public void addStorage(StorageBin storage) {
        this.storageBins.add(storage);
    }

    public List<StorageBin> getStorageBins() {
        return this.storageBins;
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.truckId == truck.id).toList();
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck, Ingredient ingredient) {
        return this.storageBins.stream()
                .filter((storageBin) -> storageBin.truckId == truck.id && storageBin.ingredient.equals(ingredient))
                .toList();
    }

    public boolean truckHasIngredient(Truck truck, Ingredient... ingredients) {
        boolean hasIngredient = false;

        for (Ingredient ingredient : ingredients) {
            for (StorageBin storageBin : this.storageBins) {
                if (storageBin.ingredient.equals(ingredient)) {
                    hasIngredient = true;

                    break;
                }
            }

            if (!hasIngredient) {
                return false;
            }
        }

        return true;
    }

    public boolean truckHasSyrupAddOns(Truck truck) {
        return storageBins.stream().anyMatch((storageBin) -> storageBin.ingredient.equals(Ingredient.HAZELNUT_SYRUP)
                || storageBin.ingredient.equals(Ingredient.CHOCOLATE_SYRUP)
                || storageBin.ingredient.equals(Ingredient.ALMOND_SYRUP)
                || storageBin.ingredient.equals(Ingredient.SWEETENER));
    }
}

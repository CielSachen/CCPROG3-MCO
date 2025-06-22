package cielsachen.mco1.service;

import java.util.Collections;
import java.util.List;

import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.StorageBin;
import cielsachen.mco1.model.Truck;

public class StorageBinService {
    private final List<StorageBin> storageBins;

    public StorageBinService(List<StorageBin> storageBins) {
        this.storageBins = storageBins;
    }

    public void addStorageBin(StorageBin storage) {
        this.storageBins.add(storage);
    }

    public List<StorageBin> getStorageBins() {
        return Collections.unmodifiableList(this.storageBins);
    }

    public StorageBin getStorageBinByTruck(Truck truck, Ingredient ingredient) {
        for (StorageBin storageBin : this.storageBins) {
            if (storageBin.truck.equals(truck) && storageBin.ingredient.equals(ingredient)
                    && storageBin.getCapacity() > 0) {
                return storageBin;
            }
        }

        return null;
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.truck.equals(truck)).toList();
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

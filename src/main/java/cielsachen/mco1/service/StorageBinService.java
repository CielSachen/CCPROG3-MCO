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

    public boolean decreaseCapacityByTruck(Truck truck, Ingredient ingredient, double amount) {
        if (this.getTotalCapacityByTruck(truck, ingredient) < amount) {
            return false;
        }

        double remainingAmount = amount;

        for (StorageBin storageBin : this.getStorageBinsByTruck(truck, ingredient)) {
            if (storageBin.getCapacity() >= remainingAmount) {
                storageBin.decreaseCapacity(remainingAmount);

                break;
            }

            remainingAmount -= storageBin.getCapacity();

            storageBin.decreaseCapacity(storageBin.getCapacity());
        }

        return true;
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck, Ingredient ingredient) {
        return this.storageBins.stream()
                .filter((storageBin) -> storageBin.truck.equals(truck) && storageBin.getIngredient().equals(ingredient))
                .toList();
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.truck.equals(truck)).toList();
    }

    public double getTotalCapacityByTruck(Truck truck, Ingredient ingredient) {
        return this.getStorageBinsByTruck(truck, ingredient).stream()
                .mapToDouble((storageBin) -> storageBin.getCapacity()).sum();
    }

    public boolean truckHasIngredient(Truck truck, Ingredient... ingredients) {
        boolean hasIngredient = false;

        for (Ingredient ingredient : ingredients) {
            for (StorageBin storageBin : this.storageBins) {
                if (storageBin.getIngredient().equals(ingredient)) {
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
        return storageBins.stream().anyMatch((storageBin) -> storageBin.getIngredient().isSpecial);
    }
}

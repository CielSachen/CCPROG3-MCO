package cielsachen.mco1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public List<StorageBin> getStorageBinsByIngredient(Ingredient ingredient) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.getIngredient().equals(ingredient)).toList();
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

    public Optional<StorageBin> getStorageBinsById(int id, Truck truck) {
        for (StorageBin storageBin : this.getStorageBinsByTruck(truck)) {
            if (storageBin.id == id) {
                return Optional.of(storageBin);
            }
        }

        return Optional.empty();
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

    public boolean truckHasSyrups(Truck truck) {
        return storageBins.stream().anyMatch((storageBin) -> storageBin.getIngredient().isSpecial);
    }
}

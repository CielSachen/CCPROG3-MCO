package cielsachen.ccprog3.mco1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cielsachen.ccprog3.mco1.model.Ingredient;
import cielsachen.ccprog3.mco1.model.StorageBin;
import cielsachen.ccprog3.mco1.model.Truck;

/**
 * Represents a service for managing storage bins. This uses a standard Java {@link List} instead of a dedicated
 * repository.
 */
public class StorageBinService {
    private final List<StorageBin> storageBins;

    /**
     * Creates and returns a new {@code StorageBinService} object instance.
     *
     * @param storageBins The list of storage bins to use.
     */
    public StorageBinService(List<StorageBin> storageBins) {
        this.storageBins = storageBins;
    }

    /**
     * Gets all storage bins belonging to any trucks.
     *
     * @return The storage bins.
     */
    public List<StorageBin> getStorageBins() {
        return Collections.unmodifiableList(this.storageBins);
    }

    /**
     * Adds a storage bin to the list.
     *
     * @param storage The storage bin to add.
     */
    public void addStorageBin(StorageBin storage) {
        this.storageBins.add(storage);
    }

    /**
     * Decreases the capacities of storage bins belonging to a truck containing an ingredient.
     * <p>
     * The storage bins are "overflowed", meaning that once a storage bin is empty, the next storage bin's capacity will
     * be decreased.
     *
     * @param truck      The truck owning the storage bins.
     * @param ingredient The ingredient contained by the storage bins.
     * @param amount     The amount to decrease the capacities by.
     * @return Whether the capacities of the storage bins were decreased.
     */
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

    /**
     * Gets the storage bin belonging to a truck with a specific ID.
     *
     * @param id    The ID of the storage bin to get.
     * @param truck The truck owning the storage bin.
     * @return The storage bin with the ID.
     */
    public Optional<StorageBin> getStorageBinsById(int id, Truck truck) {
        for (StorageBin storageBin : this.getStorageBinsByTruck(truck)) {
            if (storageBin.id == id) {
                return Optional.of(storageBin);
            }
        }

        return Optional.empty();
    }

    /**
     * Gets all storage bins containing an ingredient.
     *
     * @param ingredient The ingredient contained by the storage bins to get.
     * @return The storage bins containing the ingredient.
     */
    public List<StorageBin> getStorageBinsByIngredient(Ingredient ingredient) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.getIngredient().equals(ingredient)).toList();
    }

    /**
     * Gets all storage bins belonging to a truck containing an ingredient.
     *
     * @param truck      The truck owning the storage bins to get.
     * @param ingredient The ingredient contained by the storage bins.
     * @return The storage bins belonging to the truck containing the ingredient.
     */
    public List<StorageBin> getStorageBinsByTruck(Truck truck, Ingredient ingredient) {
        return this.storageBins.stream()
                .filter((storageBin) -> storageBin.truck.equals(truck) && storageBin.getIngredient().equals(ingredient))
                .toList();
    }

    /**
     * Gets all storage bins belonging to a truck.
     *
     * @param truck The truck owning the storage bins to get.
     * @return The storage bins belonging to the truck.
     */
    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.truck.equals(truck)).toList();
    }

    /**
     * Gets the total capacity of all storage bins belonging to a truck containing an ingredient.
     *
     * @param truck      The truck owning the storage bins.
     * @param ingredient The ingredient contained by the storage bins.
     * @return The total capacity of the storage bins.
     */
    public double getTotalCapacityByTruck(Truck truck, Ingredient ingredient) {
        return this.getStorageBinsByTruck(truck, ingredient).stream()
                .mapToDouble((storageBin) -> storageBin.getCapacity()).sum();
    }

    /**
     * Checks if a truck has any storage bins for certain ingredients.
     *
     * @param truck       The truck to check.
     * @param ingredients The ingredients to check.
     * @return Whether truck has storage bins for the ingredients.
     */
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

    /**
     * Checks if a truck has any storage bins for syrups.
     *
     * @param truck The truck to check.
     * @return Whether the truck has storage bins for syrups.
     */
    public boolean truckHasSyrups(Truck truck) {
        return storageBins.stream().anyMatch((storageBin) -> storageBin.getIngredient().isSpecial);
    }
}

package cielsachen.ccprog3.mco2.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cielsachen.ccprog3.mco2.model.Ingredient;
import cielsachen.ccprog3.mco2.model.StorageBin;
import cielsachen.ccprog3.mco2.model.Truck;

/**
 * Represents a service for managing storage bins. The business logic happens here instead of in the models.
 * <p>
 * This uses a standard Java {@link List list} instead of a dedicated repository.
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
     * Gets all storage bins containing an ingredient.
     *
     * @param ingredient The ingredient contained by the storage bins to get.
     * @return The storage bins containing the ingredient.
     */
    public List<StorageBin> getStorageBinsByIngredient(Ingredient ingredient) {
        return this.storageBins.stream().filter((sb) -> sb.getIngredient().equals(ingredient)).toList();
    }

    /**
     * Gets all storage bins belonging to a truck.
     *
     * @param truck The truck owning the storage bins to get.
     * @return The storage bins belonging to the truck.
     */
    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((sb) -> sb.truck.equals(truck)).toList();
    }

    /**
     * Gets all storage bins belonging to a truck and containing an ingredient.
     *
     * @param truck      The truck owning the storage bins to get.
     * @param ingredient The ingredient contained by the storage bins to get.
     * @return The storage bins belonging to the truck and containing the ingredient.
     */
    public List<StorageBin> getStorageBinsByTruck(Truck truck, Ingredient ingredient) {
        return this.storageBins.stream().filter((sb) -> sb.truck.equals(truck) && sb.getIngredient().equals(ingredient))
                .toList();
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
     * Gets the total capacity of all storage bins belonging to a truck and containing an ingredient.
     *
     * @param truck      The truck owning the storage bins.
     * @param ingredient The ingredient contained by the storage bins to get.
     * @return The total capacity of the storage bins belonging to the truck and containing the ingredient.
     */
    public double getTotalCapacityByTruck(Truck truck, Ingredient ingredient) {
        return this.getStorageBinsByTruck(truck, ingredient).stream()
                .mapToDouble((storageBin) -> storageBin.getCapacity()).sum();
    }

    /**
     * Gets the capacities of all ingredients in all trucks.
     *
     * @return The ingredients mapped to their capacities.
     */
    public Map<Ingredient, Double> getIngredientCapacities() {
        Map<Ingredient, Double> capacitiesByIngredient = new HashMap<Ingredient, Double>();

        for (Ingredient ingredient : Ingredient.values()) {
            capacitiesByIngredient.put(ingredient,
                    this.getStorageBinsByIngredient(ingredient).stream().mapToDouble((sb) -> sb.getCapacity()).sum());
        }

        return capacitiesByIngredient;
    }

    /**
     * Decreases the capacities of storage bins belonging to a truck and containing an ingredient.
     * <p>
     * The storage bins are "overflowed", meaning that once a storage bin is empty, the next storage bin's capacity will
     * be decreased.
     *
     * @param truck      The truck owning the storage bins.
     * @param ingredient The ingredient contained by the storage bins.
     * @param amount     The amount to decrease the capacities by.
     * @return Whether the capacities of the storage bins was decreased.
     */
    public boolean decreaseCapacityByTruck(Truck truck, Ingredient ingredient, double amount) {
        if (this.getTotalCapacityByTruck(truck, ingredient) < amount) {
            return false;
        }

        double remainingAmt = amount;

        for (StorageBin storageBin : this.getStorageBinsByTruck(truck, ingredient)) {
            if (storageBin.getCapacity() >= remainingAmt) {
                storageBin.decreaseCapacity(remainingAmt);

                break;
            }

            remainingAmt -= storageBin.getCapacity();

            storageBin.decreaseCapacity(storageBin.getCapacity());
        }

        return true;
    }

    /**
     * Checks whether a truck has storage bins containing certain ingredients.
     *
     * @param truck       The truck to check.
     * @param ingredients The ingredients to check against.
     * @return Whether the truck has storage bins containing the ingredients.
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
     * Checks whether a truck has storage bins containing syrups.
     *
     * @param truck The truck to check.
     * @return Whether the truck has storage bins containing syrups.
     */
    public boolean truckHasSyrups(Truck truck) {
        return storageBins.stream().anyMatch((storageBin) -> storageBin.getIngredient().isSpecial);
    }
}

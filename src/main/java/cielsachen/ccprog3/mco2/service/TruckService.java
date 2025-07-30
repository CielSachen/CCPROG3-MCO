package cielsachen.ccprog3.mco2.service;

import java.util.Collections;
import java.util.List;

import cielsachen.ccprog3.mco2.model.Truck;

/**
 * Represents a service for managing trucks. The business logic happens here instead of in the models.
 * <p>
 * This uses a standard Java {@link List list} instead of a dedicated repository.
 */
public class TruckService {
    private final List<Truck> trucks;

    /**
     * Creates and returns a new {@code TruckService} object instance.
     *
     * @param trucks The list of trucks to use.
     */
    public TruckService(List<Truck> trucks) {
        this.trucks = trucks;
    }

    /**
     * Gets all trucks.
     *
     * @return The trucks.
     */
    public List<Truck> getTrucks() {
        return Collections.unmodifiableList(this.trucks);
    }

    /**
     * Gets the special trucks.
     *
     * @return The special trucks.
     */
    public List<Truck> getSpecialTrucks() {
        return this.trucks.stream().filter((t) -> t.isSpecial).toList();
    }

    /**
     * Adds a truck to the list. If the truck’s location is already occupied by an existing truck, the truck will not be
     * added.
     *
     * @param truck The truck to add.
     * @return Whether the truck was added to the list.
     */
    public boolean addTruck(Truck truck) {
        if (this.isOccupiedLocation(truck.getLocation())) {
            return false;
        }

        this.trucks.add(truck);

        return true;
    }

    /**
     * Checks whether a location is occupied by another truck.
     *
     * @param location The location to check.
     * @return Whether the location is occupied by another truck.
     */
    public boolean isOccupiedLocation(String location) {
        return this.trucks.stream().anyMatch((truck) -> truck.getLocation().equals(location));
    }
}

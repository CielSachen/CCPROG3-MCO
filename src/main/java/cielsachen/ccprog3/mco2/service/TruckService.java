package cielsachen.ccprog3.mco2.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cielsachen.ccprog3.mco2.model.Truck;

/**
 * Represents a service for managing trucks. This uses a standard Java {@link List} instead of a dedicated repository.
 */
public class TruckService {
    private final List<Truck> trucks;

    /**
     * Creates a new {@code TruckService} object instance.
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
     * Adds a truck to the list if it's located in an unoccupied location.
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
     * Gets the special trucks.
     *
     * @return The special trucks.
     */
    public List<Truck> getSpecialTrucks() {
        return this.trucks.stream().filter((truck) -> truck.isSpecial).toList();
    }

    /**
     * Gets the truck with a specific ID.
     *
     * @param id The ID of the truck to get.
     * @return The truck with the ID.
     */
    public Optional<Truck> getTruckById(int id) {
        for (Truck truck : this.trucks) {
            if (truck.id == id) {
                return Optional.of(truck);
            }
        }

        return Optional.empty();
    }

    /**
     * Checks if a truck is already occupying a location.
     *
     * @param location The location to check.
     * @return Whether the location is occupied by a truck.
     */
    public boolean isOccupiedLocation(String location) {
        return this.trucks.stream().anyMatch((truck) -> truck.getLocation().equals(location));
    }
}

package cielsachen.mco1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cielsachen.mco1.model.Truck;

public class TruckService {
    private final List<Truck> trucks;

    public TruckService(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public boolean addTruck(Truck truck) {
        if (this.isOccupiedLocation(truck.getLocation())) {
            return false;
        }

        this.trucks.add(truck);

        return true;
    }

    public List<Truck> getTrucks() {
        return Collections.unmodifiableList(this.trucks);
    }

    public Optional<Truck> getTruckById(int id) {
        for (Truck truck : this.trucks) {
            if (truck.id == id) {
                return Optional.of(truck);
            }
        }

        return Optional.empty();
    }

    public boolean isOccupiedLocation(String location) {
        return this.trucks.stream().anyMatch((truck) -> truck.getLocation().equals(location));
    }
}

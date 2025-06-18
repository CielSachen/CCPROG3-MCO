package cielsachen.ccprog3.service;

import java.util.List;

import cielsachen.ccprog3.model.Truck;

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
        return this.trucks;
    }

    public Truck getTruckById(int id) {
        for (Truck truck : this.trucks) {
            if (truck.id == id) {
                return truck;
            }
        }

        return null;
    }

    public boolean isOccupiedLocation(String location) {
        return this.trucks.stream().anyMatch((truck) -> truck.getLocation().equals(location));
    }
}

package cielsachen.ccprog3.service;

import java.util.List;

import cielsachen.ccprog3.model.storagebin.StorageBin;
import cielsachen.ccprog3.model.truck.Truck;

public class StorageBinService {
    private final List<StorageBin> storageBins;

    public StorageBinService(List<StorageBin> storageBins) {
        this.storageBins = storageBins;
    }

    public List<StorageBin> getStorageBins() {
        return this.storageBins;
    }

    public List<StorageBin> getStorageBinsByTruck(Truck truck) {
        return this.storageBins.stream().filter((storageBin) -> storageBin.truckId == truck.id).toList();
    }

    public void addStorage(StorageBin storage) {
        this.storageBins.add(storage);
    }
}

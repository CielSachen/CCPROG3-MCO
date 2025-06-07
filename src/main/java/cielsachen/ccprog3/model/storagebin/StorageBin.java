package cielsachen.ccprog3.model.storagebin;

public abstract class StorageBin {
    public static final int STANDARD_TRUCK_AMOUNT = 8;
    public static final int SPECIAL_TRUCK_AMOUNT = 10;

    private double capacity = 0;
    public final int id;
    public final String itemName;
    public final int maximumCapacity;
    public final int truckId;
    public final String unitMeasure;

    protected StorageBin(int id, int truckId, String itemName, int maximumCapacity, String unitMeasure) {
        this.id = id;
        this.itemName = itemName;
        this.maximumCapacity = maximumCapacity;
        this.truckId = truckId;
        this.unitMeasure = unitMeasure;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public boolean increaseCapacity(double additionalCapacity) {
        if (additionalCapacity <= 0) {
            return false;
        }

        this.capacity += additionalCapacity;

        return true;
    }

    public boolean decreaseCapacity(double deductibleCapacity) {
        if (deductibleCapacity > this.capacity) {
            return false;
        }

        this.capacity -= deductibleCapacity;

        return true;
    }

    public String toCapacityString() {
        return this.capacity + " " + this.unitMeasure;
    }

    public String toMaximumCapacityString() {
        return this.maximumCapacity + " " + this.unitMeasure;
    }
}

package cielsachen.ccprog3.model;

public class StorageBin {
    public static final int STANDARD_TRUCK_AMOUNT = 8;
    public static final int SPECIAL_TRUCK_AMOUNT = 10;

    public final int id;
    public final Ingredient ingredient;
    public final int truckId;

    private double capacity = 0;

    public StorageBin(int id, int truckId, Ingredient ingredient) {
        this.capacity = ingredient.maximumCapacity;
        this.id = id;
        this.ingredient = ingredient;
        this.truckId = truckId;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public boolean decreaseCapacity(double deductibleCapacity) {
        if (deductibleCapacity > this.capacity) {
            return false;
        }

        this.capacity -= deductibleCapacity;

        return true;
    }

    public boolean increaseCapacity(double additionalCapacity) {
        if (additionalCapacity <= 0) {
            return false;
        }

        this.capacity += additionalCapacity;

        return true;
    }

    public boolean isCriticalCapacity() {
        return this.capacity < this.ingredient.maximumCapacity / 3;
    }

    public String toCapacityString() {
        return this.capacity + " " + this.ingredient.unitMeasure;
    }

    public String toMaximumCapacityString() {
        return this.ingredient.maximumCapacity + " " + this.ingredient.unitMeasure;
    }
}

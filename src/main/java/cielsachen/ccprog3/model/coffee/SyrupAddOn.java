package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.StorageBin;

public class SyrupAddOn {
    public static final float DEFAULT_PRICE = -1;

    private float price;

    public float getPrice() {
        return this.price;
    }

    public boolean setPrice(float newPrice) {
        if (newPrice < 0) {
            return false;
        }

        this.price = newPrice;

        return true;
    }

    public boolean add(int amount, StorageBin syrupStorageBin) {
        if (!this.canAdd(amount, syrupStorageBin)) {
            return false;
        }

        syrupStorageBin.decreaseCapacity(amount);

        return true;
    }

    public boolean canAdd(int amount, StorageBin syrupStorageBin) {
        return syrupStorageBin.getCapacity() >= amount;
    }

    public String toPriceString() {
        return this.price + " PHP";
    }
}

package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.StorageBin;
import cielsachen.ccprog3.util.UnitConversion;

public class Espresso {
    private float price = Coffee.DEFAULT_PRICE;

    public boolean brew(double amount, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin) {
        if (!this.canBrew(amount, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        coffeeBeanStorageBin.decreaseCapacity(UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount);
        waterStorageBin.decreaseCapacity(ratio.waterDecimal * amount);

        return true;
    }

    public boolean canBrew(double amount, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin) {
        if (waterStorageBin.getCapacity() < UnitConversion.fluidOuncesToGrams(ratio.coffeeBeanDecimal) * amount
                || waterStorageBin.getCapacity() < ratio.waterDecimal * amount) {
            return false;
        }

        return true;
    }

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

    public String toPriceString() {
        return this.price + " PHP";
    }
}

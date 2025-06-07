package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.storagebin.CoffeeBeanStorageBin;
import cielsachen.ccprog3.model.storagebin.WaterStorageBin;

public class Americano extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 3;
    public static final double WATER_RATIO = 2 / 3;

    public Americano(int truckId, float price) {
        super("Café Americano", truckId, price);
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        double espressoWaterAmount = Americano.ESPRESSO_RATIO * size.capacity;

        if (Espresso.canBrewShot(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || waterStorageBin.getCapacity() < espressoWaterAmount + (Americano.WATER_RATIO * size.capacity)) {
            return false;
        }

        return true;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        if (!this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        Espresso.brewShot(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        waterStorageBin.decreaseCapacity(Americano.WATER_RATIO * size.capacity);

        return true;
    }
}

package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.storagebin.CoffeeBeanStorageBin;
import cielsachen.ccprog3.model.storagebin.MilkStorageBin;
import cielsachen.ccprog3.model.storagebin.WaterStorageBin;

public class Cappuccino extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 3;
    public static final double MILK_RATIO = 2 / 3;

    public Cappuccino(int truckId, float price) {
        super("Cappuccino", truckId, price);
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin, MilkStorageBin milkStorageBin) {
        double espressoWaterAmount = Cappuccino.ESPRESSO_RATIO * size.capacity;

        if (Espresso.canBrewShot(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || milkStorageBin.getCapacity() < espressoWaterAmount + (Cappuccino.MILK_RATIO * size.capacity)
                || milkStorageBin.getCapacity() < Cappuccino.MILK_RATIO * size.capacity) {
            return false;
        }

        return true;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin, MilkStorageBin milkStorageBin) {
        if (!this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin, milkStorageBin)) {
            return false;
        }

        Espresso.brewShot(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        milkStorageBin.decreaseCapacity(Cappuccino.MILK_RATIO * size.capacity);

        return true;
    }
}

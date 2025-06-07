package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.storagebin.CoffeeBeanStorageBin;
import cielsachen.ccprog3.model.storagebin.MilkStorageBin;
import cielsachen.ccprog3.model.storagebin.WaterStorageBin;

public class Latte extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 5;
    public static final double MILK_RATIO = 4 / 5;

    public Latte(int truckId, float price) {
        super("Latte", truckId, price);
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin, MilkStorageBin milkStorageBin) {
        double espressoWaterAmount = Latte.ESPRESSO_RATIO * size.capacity;

        if (Espresso.canBrewShot(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || milkStorageBin.getCapacity() < espressoWaterAmount + (Latte.MILK_RATIO * size.capacity)
                || milkStorageBin.getCapacity() < Latte.MILK_RATIO * size.capacity) {
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

        milkStorageBin.decreaseCapacity(Latte.MILK_RATIO * size.capacity);

        return true;
    }
}

package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.StorageBin;

public class Latte extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 5;
    public static final double MILK_RATIO = 4 / 5;

    private final Espresso espresso;

    public Latte(Espresso espresso) {
        super("Latte");

        this.espresso = espresso;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin, StorageBin milkStorageBin) {
        if (!this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin, milkStorageBin)) {
            return false;
        }

        this.espresso.brew(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        milkStorageBin.decreaseCapacity(Latte.MILK_RATIO * size.capacity);

        return true;
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin, StorageBin milkStorageBin) {
        double espressoWaterAmount = Latte.ESPRESSO_RATIO * size.capacity;

        if (!this.espresso.canBrew(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || milkStorageBin.getCapacity() < espressoWaterAmount + (Latte.MILK_RATIO * size.capacity)
                || milkStorageBin.getCapacity() < Latte.MILK_RATIO * size.capacity) {
            return false;
        }

        return true;
    }
}

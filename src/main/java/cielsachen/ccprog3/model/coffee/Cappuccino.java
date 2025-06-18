package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.StorageBin;

public class Cappuccino extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 3;
    public static final double MILK_RATIO = 2 / 3;

    private final Espresso espresso;

    public Cappuccino(Espresso espresso) {
        super("Cappuccino");

        this.espresso = espresso;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin, StorageBin milkStorageBin) {
        if (!this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin, milkStorageBin)) {
            return false;
        }

        this.espresso.brew(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        milkStorageBin.decreaseCapacity(Cappuccino.MILK_RATIO * size.capacity);

        return true;
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin, StorageBin milkStorageBin) {
        double espressoWaterAmount = Cappuccino.ESPRESSO_RATIO * size.capacity;

        if (!this.espresso.canBrew(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || milkStorageBin.getCapacity() < espressoWaterAmount + (Cappuccino.MILK_RATIO * size.capacity)
                || milkStorageBin.getCapacity() < Cappuccino.MILK_RATIO * size.capacity) {
            return false;
        }

        return true;
    }
}

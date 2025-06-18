package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.StorageBin;

public class Americano extends Coffee {
    public static final double ESPRESSO_RATIO = 1 / 3;
    public static final double WATER_RATIO = 2 / 3;

    private final Espresso espresso;

    public Americano(Espresso espresso) {
        super("Café Americano");

        this.espresso = espresso;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin) {
        if (!this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        this.espresso.brew(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        waterStorageBin.decreaseCapacity(Americano.WATER_RATIO * size.capacity);

        return true;
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, StorageBin coffeeBeanStorageBin,
            StorageBin waterStorageBin) {
        double espressoWaterAmount = Americano.ESPRESSO_RATIO * size.capacity;

        if (!this.espresso.canBrew(espressoWaterAmount, ratio, coffeeBeanStorageBin, waterStorageBin)
                || waterStorageBin.getCapacity() < espressoWaterAmount + (Americano.WATER_RATIO * size.capacity)) {
            return false;
        }

        return true;
    }
}

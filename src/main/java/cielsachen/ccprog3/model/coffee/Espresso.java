package cielsachen.ccprog3.model.coffee;

import cielsachen.ccprog3.model.storagebin.CoffeeBeanStorageBin;
import cielsachen.ccprog3.model.storagebin.WaterStorageBin;
import cielsachen.ccprog3.util.UnitConversion;

public class Espresso extends Coffee {
    public static final double LIGHT_COFFEE_BEAN_RATIO = 1 / 16;
    public static final double LIGHT_WATER_RATIO = 15 / 16;
    public static final double STANDARD_COFFEE_BEAN_RATIO = 1 / 19;
    public static final double STANDARD_WATER_RATIO = 18 / 19;
    public static final double STRONG_COFFEE_BEAN_RATIO = 1 / 21;
    public static final double STRONG_WATER_RATIO = 20 / 21;

    private int extraShotPrice = 0;

    public Espresso(int truckId, float price) {
        super("Espresso", truckId, price);
    }

    public int getExtraShotPrice() {
        return this.extraShotPrice;
    }

    public void setExtraShotPrice(int newExtraShotPrice) {
        this.extraShotPrice = newExtraShotPrice;
    }

    public static boolean canBrewShot(double amount, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        if (waterStorageBin.getCapacity() < UnitConversion.fluidOuncesToGrams(Espresso.STANDARD_COFFEE_BEAN_RATIO)
                * amount || waterStorageBin.getCapacity() < Espresso.STANDARD_WATER_RATIO * amount) {
            return false;
        }

        return true;
    }

    public static boolean brewShot(double amount, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        if (!Espresso.canBrewShot(amount, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        coffeeBeanStorageBin
                .decreaseCapacity(UnitConversion.fluidOuncesToGrams(Espresso.STANDARD_COFFEE_BEAN_RATIO) * amount);
        waterStorageBin.decreaseCapacity(Espresso.STANDARD_WATER_RATIO * amount);

        return true;
    }

    public boolean canBrew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        if (!Espresso.canBrewShot(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        return true;
    }

    public boolean brew(CoffeeSize size, EspressoRatio ratio, CoffeeBeanStorageBin coffeeBeanStorageBin,
            WaterStorageBin waterStorageBin) {
        if (this.canBrew(size, ratio, coffeeBeanStorageBin, waterStorageBin)) {
            return false;
        }

        Espresso.brewShot(size.capacity, ratio, coffeeBeanStorageBin, waterStorageBin);

        return true;
    }
}

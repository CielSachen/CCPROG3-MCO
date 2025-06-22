package cielsachen.mco1.util;

public final class UnitConversion {
    public static final double FLUID_ONCES_TO_GRAMS = 28.34952;

    private UnitConversion() {
    }

    public static double fluidOuncesToGrams(double fluidOunces) {
        return fluidOunces * UnitConversion.FLUID_ONCES_TO_GRAMS;
    }

    public static double gramsToFluidOunces(double grams) {
        return grams / UnitConversion.FLUID_ONCES_TO_GRAMS;
    }
}

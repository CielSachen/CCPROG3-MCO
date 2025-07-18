package cielsachen.mco1.util;

/** Contains utility methods for converting from one unit of measure to another. */
public final class UnitConversion {
    /** The number of grams in a fluid ounce. */
    public static final double FLUID_ONCES_TO_GRAMS = 28.34952;

    private UnitConversion() {
    }

    /**
     * Converts fluid ounces into the equivalent grams.
     * 
     * @param fluidOunces The fluid ounces to convert.
     * @return The converted number of grams.
     */
    public static double fluidOuncesToGrams(double fluidOunces) {
        return fluidOunces * UnitConversion.FLUID_ONCES_TO_GRAMS;
    }

    /**
     * Converts grams into the equivalent fluid ounces.
     * 
     * @param grams The grams to convert.
     * @return The converted number of fluid ounces.
     */
    public static double gramsToFluidOunces(double grams) {
        return grams / UnitConversion.FLUID_ONCES_TO_GRAMS;
    }
}

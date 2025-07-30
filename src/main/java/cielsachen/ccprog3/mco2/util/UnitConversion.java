package cielsachen.ccprog3.mco2.util;

/** The utility methods for converting from one unit of measure to another. */
public class UnitConversion {
    /** The number of grams in a fluid ounce. */
    public static final double FLUID_ONCES_TO_GRAMS = 28.34952;

    private UnitConversion() {
    }

    /**
     * Converts fluid ounces (fl oz) into the equivalent grams (g).
     *
     * @param fluidOunces The fluid ounces (fl oz) to convert.
     * @return The converted number of grams (g).
     */
    public static double fluidOuncesToGrams(double fluidOunces) {
        return fluidOunces * UnitConversion.FLUID_ONCES_TO_GRAMS;
    }

    /**
     * Converts grams (g) into the equivalent fluid ounces (fl oz).
     *
     * @param grams The grams (g) to convert.
     * @return The converted number of fluid ounces (fl oz).
     */
    public static double gramsToFluidOunces(double grams) {
        return grams / UnitConversion.FLUID_ONCES_TO_GRAMS;
    }
}

package cielsachen.ccprog3.mco1.helper;

/** Represents a message describing or giving a remedy for an exception. */
public enum ExceptionMessage {
    /** The message giving a remedy for an invalid boolean input. */
    INVALID_BOOLEAN_INPUT("Please only input `true` or `false`!"),
    /** The message giving a remedy for choosing an invalid character. */
    INVALID_CHARACTER_CHOICE("Please only chose from the given character choices!"),
    /** The message giving a remedy for an invalid float input. */
    INVALID_FLOAT_INPUT("Please only input a floating point number!"),
    /** The message giving a remedy for choosing an invalid integer. */
    INVALID_INTEGER_CHOICE("Please only chose from the given integer choices!"),
    /** The message giving a remedy for an invalid integer input. */
    INVALID_INTEGER_INPUT("Please only input a whole number!");

    /** The message for the exception. */
    public final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    /**
     * Prints a custom message with proper formatting.
     * 
     * @param message The message to print.
     */
    public static void printCustom(String message) {
        System.out.println(PrintColor.set(message, PrintColor.RED));
    }

    /** Prints the exception's message with proper formatting. */
    public void print() {
        System.out.println(PrintColor.set(this.message, PrintColor.RED));
    }

    /**
     * Gets the message for the exception.
     * 
     * @return The message for the exception.
     */
    @Override
    public String toString() {
        return this.message;
    }
}

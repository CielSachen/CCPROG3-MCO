package cielsachen.mco1.helper;

public enum ExceptionMessage {
    INVALID_BOOLEAN_INPUT("Please only input `true` or `false`!"),
    INVALID_CHARACTER_CHOICE("Please only chose from the given character choices!"),
    INVALID_FLOAT_INPUT("Please only input a floating point number!"),
    INVALID_INTEGER_CHOICE("Please only chose from the given integer choices!"),
    INVALID_INTEGER_INPUT("Please only input a whole number!");

    public final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    public static void printCustom(String message) {
        System.out.println(PrintColor.set(message, PrintColor.RED));
    }

    public void print() {
        System.out.println(PrintColor.set(this.message, PrintColor.RED));
    }

    @Override
    public String toString() {
        return this.message;
    }
}

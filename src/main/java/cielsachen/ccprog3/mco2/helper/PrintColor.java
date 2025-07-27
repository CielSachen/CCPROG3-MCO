package cielsachen.ccprog3.mco2.helper;

/** Represents a ANSI escape code for printing text in a different color. */
public enum PrintColor {
    /** The ANSI escape code for printing text in bright cyan. */
    BRIGHT_CYAN("0;96"),
    /** The ANSI escape code for printing text in bright green. */
    BRIGHT_GREEN("0;92"),
    /** The ANSI escape code for printing text in bright yellow. */
    BRIGHT_YELLOW("0;93"),
    /** The ANSI escape code for printing text in red. */
    RED("0;31"),
    /** The ANSI escape code for printing text in the default color. */
    RESET("0"),
    /** The ANSI escape code for printing text in yellow. */
    YELLOW("0;33");

    /** The ANSI escape code of the color. */
    public final String escapeCode;

    private PrintColor(String code) {
        this.escapeCode = "\033[" + code + "m";
    }

    /**
     * Sets the print color of a piece of text.
     * 
     * @param text  The text to colorize.
     * @param color The color to set to.
     * @return The colorized piece of text.
     */
    public static String set(String text, PrintColor color) {
        return color + text + PrintColor.RESET;
    }

    /**
     * Gets the ANSI escape code of the color.
     * 
     * @return The ANSI escape code of the color.
     */
    @Override
    public String toString() {
        return this.escapeCode;
    }
}

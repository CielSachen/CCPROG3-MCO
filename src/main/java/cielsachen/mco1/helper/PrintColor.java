package cielsachen.mco1.helper;

public enum PrintColor {
    RED("0;31"),
    YELLOW("0;33"),
    BRIGHT_GREEN("0;92"),
    BRIGHT_YELLOW("0;93"),
    BRIGHT_CYAN("0;96"),
    RESET("0");

    public final String escapeCode;

    private PrintColor(String code) {
        this.escapeCode = "\033[" + code + "m";
    }

    public static String set(String text, PrintColor color) {
        return color + text + PrintColor.RESET;
    }

    @Override
    public String toString() {
        return this.escapeCode;
    }
}

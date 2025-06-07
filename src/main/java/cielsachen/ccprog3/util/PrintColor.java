package cielsachen.ccprog3.util;

public enum PrintColor {
    RED("\033[0;31m"),
    YELLOW("\033[0;33m"),
    BRIGHT_GREEN("\033[0;92m"),
    BRIGHT_YELLOW("\033[0;93m"),
    BRIGHT_CYAN("\033[0;96m"),
    RESET("\033[0m");

    public final String code;

    private PrintColor(String code) {
        this.code = code;
    }

    public static String setColor(String text, PrintColor color) {
        return color + text + PrintColor.RESET;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

package cielsachen.ccprog3.mco1.helper;

/** Contains helper methods for printing formatted text CLI elements. */
public final class Output {
    private Output() {
    }

    /**
     * Prints a header with the proper formatting.
     * 
     * @param header The header to print.
     */
    public static void printHeader1(String header) {
        System.out.println("---------- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + ----------");
    }

    /**
     * Prints a header with the proper formatting.
     * 
     * @param header The header to print.
     */
    public static void printHeader2(String header) {
        System.out.println("----- + ----- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + ----- + -----");
    }

    /**
     * Prints a header with the proper formatting.
     * 
     * @param header The header to print.
     */
    public static void printHeader3(String header) {
        System.out.println("----- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + -----");
    }

    /**
     * Prints a title with the proper formatting.
     * 
     * @param title The title to print.
     */
    public static void printTitle(String title) {
        System.out.println("---------- + ---------- + " + PrintColor.set(title, PrintColor.BRIGHT_YELLOW)
                + " + ---------- + ----------");
    }
}

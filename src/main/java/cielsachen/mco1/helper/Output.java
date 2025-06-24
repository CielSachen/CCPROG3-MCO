package cielsachen.mco1.helper;

public final class Output {
    private Output() {
    }

    public static void printHeader1(String header) {
        System.out.println("---------- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + ----------");
    }

    public static void printHeader2(String header) {
        System.out.println("----- + ----- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + ----- + -----");
    }

    public static void printHeader3(String header) {
        System.out.println("----- +  " + PrintColor.set(header, PrintColor.BRIGHT_YELLOW) + " + -----");
    }

    public static void printTitle(String title) {
        System.out.println("---------- + ---------- + " + PrintColor.set(title, PrintColor.BRIGHT_YELLOW)
                + " + ---------- + ----------");
    }
}

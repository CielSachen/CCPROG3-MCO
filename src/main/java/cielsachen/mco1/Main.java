package cielsachen.mco1;

/** Represents the entry point class for Java programs. */
public class Main {
    /** Creates a new {@code Main} object instance. */
    public Main() {
    }

    /**
     * Executes the Java program.
     * 
     * @param arguments The CLI arguments.
     */
    public static void main(String[] arguments) {
        var simulator = new Simulator();

        simulator.launch();
    }
}

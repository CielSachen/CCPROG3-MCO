package cielsachen.ccprog3.mco2.model;

/** Represents a coffee truck. */
public class Truck {
    /** The one-based ID of the truck. */
    public final int id;
    /** Whether the truck is special. */
    public final boolean isSpecial;

    private String location;

    /**
     * Creates and returns a new {@code Truck} object instance.
     *
     * @param id        The ID of the truck.
     * @param location  The location of the truck.
     * @param isSpecial Whether the truck is special.
     */
    public Truck(int id, String location, boolean isSpecial) {
        this.id = id;
        this.isSpecial = isSpecial;

        this.location = location;
    }

    /**
     * Gets the location of the truck.
     *
     * @return The location of the truck.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets the location of the truck.
     *
     * @param newLocation The new location to use.
     */
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    /**
     * Converts the truck into its string representation.
     *
     * @return The stylized location of the truck.
     */
    @Override
    public String toString() {
        return "Located at: " + this.getLocation();
    }
}

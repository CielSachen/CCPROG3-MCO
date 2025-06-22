package cielsachen.mco1.model;

public class Truck {
    public final int id;
    public final boolean isSpecial;

    private String location;

    public Truck(int id, String location, boolean isSpecial) {
        this.id = id;
        this.isSpecial = isSpecial;
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

}

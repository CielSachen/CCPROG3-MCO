package cielsachen.ccprog3.model.truck;

public class Truck {
    public final int id;
    private String location;

    public Truck(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }
}

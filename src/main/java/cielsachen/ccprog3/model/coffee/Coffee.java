package cielsachen.ccprog3.model.coffee;

public abstract class Coffee {
    public final String name;
    public final float price;
    public final int truckId;

    protected Coffee(String name, int truckId, float price) {
        this.name = name;
        this.price = price;
        this.truckId = truckId;
    }
}

package cielsachen.mco1.model.coffee;

public enum CoffeeSize {
    SMALL_CUP("Small Cup", 8),
    MEDIUM_CUP("Medium Cup", 12),
    LARGE_CUP("Large Cup", 16);

    public final int capacity;
    public final String name;
    public final String unitMeasure = "fl oz";

    private CoffeeSize(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.capacity + " " + this.unitMeasure;
    }
}

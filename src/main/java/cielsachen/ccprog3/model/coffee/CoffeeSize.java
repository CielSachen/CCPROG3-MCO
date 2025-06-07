package cielsachen.ccprog3.model.coffee;

public enum CoffeeSize {
    SMALL_CUP(8),
    MEDIUM_CUP(12),
    LARGE_CUP(16);

    public final int capacity;
    public final String unitMeasure = "fl oz";

    private CoffeeSize(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.capacity + " " + this.unitMeasure;
    }
}

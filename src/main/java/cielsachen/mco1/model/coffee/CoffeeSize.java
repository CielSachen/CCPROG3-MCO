package cielsachen.mco1.model.coffee;

import cielsachen.mco1.model.Ingredient;

public enum CoffeeSize {
    SMALL_CUP("Small Cup", 8, Ingredient.SMALL_CUP),
    MEDIUM_CUP("Medium Cup", 12, Ingredient.MEDIUM_CUP),
    LARGE_CUP("Large Cup", 16, Ingredient.LARGE_CUP);

    public final int capacity;
    public final Ingredient cup;
    public final String name;
    public final String unitMeasure = "fl oz";

    private CoffeeSize(String name, int capacity, Ingredient cup) {
        this.capacity = capacity;
        this.cup = cup;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.capacity + " " + this.unitMeasure;
    }
}

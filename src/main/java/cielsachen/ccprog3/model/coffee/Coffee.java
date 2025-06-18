package cielsachen.ccprog3.model.coffee;

public abstract class Coffee {
    public static final float DEFAULT_PRICE = -1;
    public static final float PRICE_SIZE_MODIFIER = 0.1f;

    public final String name;

    private float basePrice;

    protected Coffee(String name) {
        this.name = name;
        this.basePrice = Coffee.DEFAULT_PRICE;
    }

    public float getPrice() {
        return this.basePrice;
    }

    public float getPrice(CoffeeSize size) {
        switch (size) {
            case CoffeeSize.SMALL_CUP:
                return this.basePrice - this.basePrice * Coffee.PRICE_SIZE_MODIFIER;
            case CoffeeSize.LARGE_CUP:
                return this.basePrice + this.basePrice * Coffee.PRICE_SIZE_MODIFIER;
            default:
                return this.basePrice;
        }
    }

    public boolean setBasePrice(float newBasePrice) {
        if (newBasePrice < 0) {
            return false;
        }

        this.basePrice = newBasePrice;

        return true;
    }

    public String toPriceString() {
        return this.getPrice(CoffeeSize.SMALL_CUP) + " PHP /" + this.basePrice + " PHP / "
                + this.getPrice(CoffeeSize.LARGE_CUP) + " PHP";
    }

    public String toPriceString(CoffeeSize size) {
        return this.getPrice(size) + " PHP";
    }
}

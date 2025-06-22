package cielsachen.mco1.model;

public class Product {
    public static final float DEFAULT_PRICE = -1;

    protected float price = Product.DEFAULT_PRICE;

    public float getPrice() {
        return this.price;
    }

    public boolean setPrice(float newPrice) {
        if (newPrice < 0) {
            return false;
        }

        this.price = newPrice;

        return true;
    }

    public String toPriceString() {
        return this.price + " PHP";
    }
}

package cielsachen.ccprog3.mco2.model;

/** Represents a sellable product. */
public class Product {
    /** The default initial price for products. */
    public static final float DEFAULT_PRICE = -1;

    /** The price of the product. */
    protected float price = Product.DEFAULT_PRICE;

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public float getPrice() {
        return this.price;
    }

    /**
     * Sets the price of the product.
     *
     * @param newPrice The new price to use.
     * @return Whether the price of the product was changed.
     */
    public boolean setPrice(float newPrice) {
        if (newPrice < 0) {
            return false;
        }

        this.price = newPrice;

        return true;
    }

    /**
     * Converts the price of the product into a localized string.
     *
     * @return A localized price string.
     */
    public String toPriceString() {
        return this.price + " PHP";
    }
}

package cielsachen.ccprog3.mco2.view.component;

import java.util.List;

import javax.swing.JTable;

import cielsachen.ccprog3.mco2.model.coffee.Coffee;
import cielsachen.ccprog3.mco2.model.coffee.CoffeeSize;

/** Represents a table containing a coffee’s prices per size. */
public class CoffeePricesTable extends JTable {
    /**
     * Creates and returns a new {@code CoffeePricesTable} object instance.
     *
     * @param coffees The coffees to use.
     */
    public CoffeePricesTable(List<Coffee> coffees) {
        super(coffees.stream()
                .map((c) -> new String[] { c.name, c.toPriceString(CoffeeSize.SMALL_CUP),
                        c.toPriceString(CoffeeSize.MEDIUM_CUP), c.toPriceString(CoffeeSize.LARGE_CUP) })
                .toArray(String[][]::new), new String[] { "Coffee", "Price (S)", "Price (M)", "Price (L)" });
    }
}

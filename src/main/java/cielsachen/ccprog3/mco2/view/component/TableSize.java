package cielsachen.ccprog3.mco2.view.component;

import java.awt.Dimension;

/** Represents the size of a table in pixels. */
public enum TableSize {
    /** A small table. */
    SMALL(220, 120),
    /** A medium table. */
    MEDIUM(330, 160),
    /** A large table. */
    LARGE(440, 200);

    /** The width of the table. */
    public final int width;
    /** The height of the table. */
    public final int height;
    /** The dimension of the table. */
    public final Dimension dimension;

    private TableSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.dimension = new Dimension(width, height);
    }
}

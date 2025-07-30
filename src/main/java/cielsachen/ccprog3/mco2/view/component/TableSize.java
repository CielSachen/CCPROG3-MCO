package cielsachen.ccprog3.mco2.view.component;

import java.awt.Dimension;

public enum TableSize {
    SMALL(220, 120),
    MEDIUM(330, 160),
    LARGE(440, 200);

    public final int width;
    public final int height;
    public final Dimension dimension;

    private TableSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.dimension = new Dimension(width, height);
    }
}

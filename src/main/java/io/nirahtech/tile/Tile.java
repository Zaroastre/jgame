package io.nirahtech.tile;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage image;
    public boolean isCollision = false;

    protected Tile(final BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.hashCode(), this.isCollision);
    }
}

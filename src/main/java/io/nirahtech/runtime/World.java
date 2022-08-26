package io.nirahtech.runtime;

public class World {
    private final int width;
    private final int height;

    public World(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

package io.nirahtech.tile;

import java.awt.image.BufferedImage;

public class WorldMap {
    private static final boolean isWater(final int red, final int green, final int blue) {
        return ((red == 0) && (green == 0) && (blue == 255));
    }

    private static final boolean isGrass(final int red, final int green, final int blue) {
        return ((red == 50) && (green == 255) && (blue == 50));
    }

    private static final boolean isForest(final int red, final int green, final int blue) {
        return ((red == 0) && (green == 150) && (blue == 0));
    }

    private static final boolean isSand(final int red, final int green, final int blue) {
        return ((red == 255) && (green == 255) && (blue == 0));
    }

    private static final boolean isHotStep(final int red, final int green, final int blue) {
        return ((red == 255) && (green == 100) && (blue == 0));
    }

    private static final boolean isEarth(final int red, final int green, final int blue) {
        return ((red == 150) && (green == 100) && (blue == 50));
    }

    private static final boolean isIce(final int red, final int green, final int blue) {
        return ((red == 255) && (green == 255) && (blue == 255));
    }

    public static final char[][] loadWorldMapImage(final BufferedImage worldMapImage) {
        final int width = worldMapImage.getWidth();
        final int height = worldMapImage.getHeight();

        char[][] world = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = worldMapImage.getRGB(x, y);
                // int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                if (isWater(red, green, blue)) {
                    world[y][x] = '0';
                } else if (isGrass(red, green, blue)) {
                    world[y][x] = '1';
                } else if (isForest(red, green, blue)) {
                    world[y][x] = '2';
                } else if (isSand(red, green, blue)) {
                    world[y][x] = '3';
                } else if (isHotStep(red, green, blue)) {
                    world[y][x] = '4';
                } else if (isEarth(red, green, blue)) {
                    world[y][x] = '5';
                } else if (isIce(red, green, blue)) {
                    world[y][x] = '6';
                }
            }
        }
        return world;
    }
}

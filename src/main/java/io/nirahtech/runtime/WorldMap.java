package io.nirahtech.runtime;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import io.nirahtech.gui.GamePanel;
import io.nirahtech.tile.EarthTile;
import io.nirahtech.tile.ForestTile;
import io.nirahtech.tile.GrassTile;
import io.nirahtech.tile.HotStepTile;
import io.nirahtech.tile.IceTile;
import io.nirahtech.tile.SandTile;
import io.nirahtech.tile.Tile;
import io.nirahtech.tile.WaterTile;

public class WorldMap {
    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getSimpleName());
    private static BufferedImage grassImage;
    private static BufferedImage waterImage;
    private static BufferedImage hotStepImage;
    private static BufferedImage sandImage;
    private static BufferedImage iceImage;
    private static BufferedImage forestImage;
    private static BufferedImage earthImage;

    static {
        LOGGER.info("Loading tiles images...");
        loadTileImage();
    }

    public static final class WorldMapBuilder {
        private BufferedImage image;
        private int originalTileSizeWidth;
        private int originalTileSizeHeight;
        private int tileSizeScale;

        public final WorldMapBuilder image(final BufferedImage image) {
            this.image = image;
            return this;
        }

        public final WorldMapBuilder originalTileSizeWidth(final int originalTileSizeWidth) {
            this.originalTileSizeWidth = originalTileSizeWidth;
            return this;
        }

        public final WorldMapBuilder originalTileSizeHeight(final int originalTileSizeHeight) {
            this.originalTileSizeHeight = originalTileSizeHeight;
            return this;
        }

        public final WorldMapBuilder originalTileSize(final int originalTileSize) {
            this.originalTileSizeWidth = originalTileSize;
            this.originalTileSizeHeight = originalTileSize;
            return this;
        }

        public final WorldMapBuilder tileSizeScale(final int tileSizeScale) {
            this.tileSizeScale = tileSizeScale;
            return this;
        }

        public final WorldMap build() {
            LOGGER.info("Building the world map!");
            return new WorldMap(this);
        }

    }

    private final BufferedImage image;
    private final Tile[][] map;
    private final int originalTileSizeWidth;
    private final int originalTileSizeHeight;
    private final int tileSizeScale;

    private WorldMap(final WorldMapBuilder builder) {
        LOGGER.info("Building the world map...");
        this.image = builder.image;
        this.originalTileSizeWidth = builder.originalTileSizeWidth;
        this.originalTileSizeHeight = builder.originalTileSizeHeight;
        this.tileSizeScale = builder.tileSizeScale;
        this.map = new Tile[this.image.getHeight()][this.image.getWidth()];
        this.buildWorldMap();
    }

    private void buildWorldMap() {
        for (int y = 0; y < this.image.getHeight(); y++) {
            for (int x = 0; x < this.image.getWidth(); x++) {
                Tile tile = null;
                int pixel = this.image.getRGB(x, y);
                // int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                if (isWater(red, green, blue)) {
                    tile = new WaterTile(WorldMap.waterImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                    tile.isCollision = true;
                } else if (isGrass(red, green, blue)) {
                    tile = new GrassTile(WorldMap.grassImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                } else if (isForest(red, green, blue)) {
                    tile = new ForestTile(WorldMap.forestImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                    tile.isCollision = true;
                } else if (isSand(red, green, blue)) {
                    tile = new SandTile(WorldMap.sandImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                } else if (isHotStep(red, green, blue)) {
                    tile = new HotStepTile(WorldMap.hotStepImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                } else if (isEarth(red, green, blue)) {
                    tile = new EarthTile(WorldMap.earthImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                } else if (isIce(red, green, blue)) {
                    tile = new IceTile(WorldMap.iceImage.getSubimage(0, 0, this.originalTileSizeWidth,
                            this.originalTileSizeHeight));
                }
                this.map[y][x] = tile;
            }
        }
    }

    private static void loadTileImage() {

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("grass.png")) {
            WorldMap.grassImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("water.png")) {
            WorldMap.waterImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("ice.png")) {
            WorldMap.iceImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("hotStep.png")) {
            WorldMap.hotStepImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("tree.png")) {
            WorldMap.forestImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("earth.png")) {
            WorldMap.earthImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = WorldMap.class.getClassLoader().getResourceAsStream("sand.png")) {
            WorldMap.sandImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public int getOriginalHeight() {
        return this.image.getHeight();
    }

    public int getOriginalWidth() {
        return this.image.getWidth();
    }

    public int getWidth() {
        return this.getOriginalWidth() * (this.originalTileSizeWidth * this.tileSizeScale);
    }

    public int getHeight() {
        return this.getOriginalHeight() * (this.originalTileSizeHeight * this.tileSizeScale);
    }

    public Tile getTile(final int x, final int y) {
        return this.map[y][x];
    }
}

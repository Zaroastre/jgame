package io.nirahtech.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.nirahtech.runtime.GamePanel;
import io.nirahtech.runtime.GameProcess;

public final class TileManager implements GameProcess {
    private final GamePanel gamePanel;
    private Tile[][] map = null;
    private BufferedImage grassImage = null;
    private BufferedImage waterImage = null;
    private BufferedImage hotStepImage = null;
    private BufferedImage sandImage = null;
    private BufferedImage iceImage = null;
    private BufferedImage forestImage = null;
    private BufferedImage earthImage = null;

    public TileManager(final GamePanel gamePanel, final int totalTiles) {
        this.gamePanel = gamePanel;
        this.loadTileImage();
        this.loadMap();
    }

    private void loadTileImage() {

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("grass.png")) {
            this.grassImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("water.png")) {
            this.waterImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("ice.png")) {
            this.iceImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("hotStep.png")) {
            this.hotStepImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("tree.png")) {
            this.forestImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("earth.png")) {
            this.earthImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("sand.png")) {
            this.sandImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    private void loadMap() {

        BufferedImage worldMapImage = null;
        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("worldmap.png")) {
            worldMapImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[][] loadedMap = WorldMap.loadWorldMapImage(worldMapImage);
        this.map = new Tile[worldMapImage.getHeight()][worldMapImage.getWidth()];

        for (int y = 0; y < worldMapImage.getHeight(); y++) {
            for (int x = 0; x < worldMapImage.getWidth(); x++) {
                Tile tile = null;
                switch (loadedMap[y][x]) {
                    case '0':
                        tile = new WaterTile(this.waterImage);
                        tile.isCollision = true;
                        break;
                    case '1':
                        tile = new GrassTile(this.grassImage);
                        break;
                    case '2':
                        tile = new ForestTile(this.forestImage);
                        tile.isCollision = true;
                        break;
                    case '3':
                        tile = new SandTile(this.sandImage);
                        break;
                    case '4':
                        tile = new HotStepTile(this.hotStepImage);
                        break;
                    case '5':
                        tile = new EarthTile(this.earthImage);
                        break;
                    case '6':
                        tile = new IceTile(this.iceImage);
                        break;

                    default:
                        break;
                }
                this.map[y][x] = tile;
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (int indexY = 0; indexY < this.gamePanel.getScreenHeight(); indexY++) {
            for (int indexX = 0; indexX < this.gamePanel.getScreenWidth(); indexX++) {
                Tile tile = this.map[indexY][indexX];

                int worldX = indexX * this.gamePanel.getTileSize();
                int worldY = indexY * this.gamePanel.getTileSize();
                int screenX = worldX - this.gamePanel.getPlayer().getWorldX() + this.gamePanel.getPlayer().getScreenX();
                int screenY = worldY - this.gamePanel.getPlayer().getWorldY() + this.gamePanel.getPlayer().getScreenY();

                if (worldX + this.gamePanel.getTileSize() > this.gamePanel.getPlayer().getWorldX()
                        - this.gamePanel.getPlayer().getScreenX() &&
                        worldX - this.gamePanel.getTileSize() < this.gamePanel.getPlayer().getWorldX()
                                + this.gamePanel.getPlayer().getScreenX()
                        &&
                        worldY + this.gamePanel.getTileSize() > this.gamePanel.getPlayer().getWorldY()
                                - this.gamePanel.getPlayer().getScreenY()
                        &&
                        worldY - this.gamePanel.getTileSize() < this.gamePanel.getPlayer().getWorldY()
                                + this.gamePanel.getPlayer().getScreenY()) {

                    graphics.drawImage(
                            tile.getImage(),
                            screenX,
                            screenY,
                            this.gamePanel.getTileSize(),
                            this.gamePanel.getTileSize(),
                            null);
                }
            }
        }
    }

    public Tile[][] getMap() {
        return map;
    }
}

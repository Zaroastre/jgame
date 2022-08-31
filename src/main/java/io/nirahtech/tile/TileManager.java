package io.nirahtech.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import io.nirahtech.runtime.GamePanel;
import io.nirahtech.runtime.GameProcess;
import io.nirahtech.runtime.Initializable;
import io.nirahtech.runtime.WorldMap;

public final class TileManager implements GameProcess, Initializable {

    private static TileManager instance;

    public static final TileManager getInstance() {
        if (TileManager.instance == null) {
            TileManager.instance = new TileManager();
        }
        return TileManager.instance;
    }

    private GamePanel gamePanel;
    private WorldMap map;

    private TileManager() {

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void paintComponent(Graphics graphics) {
        // For each pixel on Y axe (screen)...
        for (int screenIndexY = 0; screenIndexY < this.gamePanel.getScreenHeight(); screenIndexY++) {
            // For each pixel on X axe (screen)...
            for (int screenIndexX = 0; screenIndexX < this.gamePanel.getScreenWidth(); screenIndexX++) {
                Tile tile = this.map.getTile(screenIndexX, screenIndexY);

                // Real potential displayed world position if each pixel on screen.
                int worldIndexX = screenIndexX * this.gamePanel.getTileSize();
                int worldIndexY = screenIndexY * this.gamePanel.getTileSize();

                // Setup pixel location on the screen
                int screenX = worldIndexX - this.gamePanel.getPlayer().getWorldX()
                        + this.gamePanel.getPlayer().getScreenX();
                int screenY = worldIndexY - this.gamePanel.getPlayer().getWorldY()
                        + this.gamePanel.getPlayer().getScreenY();

                if (worldIndexX + this.gamePanel.getTileSize() > this.gamePanel.getPlayer().getWorldX()
                        - this.gamePanel.getPlayer().getScreenX() &&
                        worldIndexX - this.gamePanel.getTileSize() < this.gamePanel.getPlayer().getWorldX()
                                + this.gamePanel.getPlayer().getScreenX()
                        &&
                        worldIndexY + this.gamePanel.getTileSize() > this.gamePanel.getPlayer().getWorldY()
                                - this.gamePanel.getPlayer().getScreenY()
                        &&
                        worldIndexY - this.gamePanel.getTileSize() < this.gamePanel.getPlayer().getWorldY()
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

    public WorldMap getWorldMap() {
        return this.map;
    }

    @Override
    public void initialize(ResourceBundle configuration) {
        this.gamePanel = GamePanel.getInstance();
        BufferedImage worldMapImage = null;
        try (final InputStream inputStream = TileManager.class.getClassLoader().getResourceAsStream("worldmap.png")) {
            worldMapImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.map = new WorldMap.WorldMapBuilder()
                .image(worldMapImage)
                .originalTileSize(this.gamePanel.getOriginalTileSize())
                .tileSizeScale(this.gamePanel.getScale())
                .build();
    }
}

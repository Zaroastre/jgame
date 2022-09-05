package io.nirahtech.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import io.nirahtech.entities.characters.Player;
import io.nirahtech.gui.GamePanel;
import io.nirahtech.runtime.Initializable;
import io.nirahtech.runtime.WorldMap;
import io.nirahtech.runtime.apis.GameProcess;

public final class TileManager implements GameProcess, Initializable {
    private static final Logger LOGGER = Logger.getLogger(TileManager.class.getSimpleName());

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
        LOGGER.info("Creating instance of tile manager.");
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
                Player player = this.gamePanel.getPlayer();

                // Setup pixel location on the screen
                int screenX = worldIndexX - player.getPositionOnTheWorldMap().x
                        + player.getPositionOnTheScreen().x;
                int screenY = worldIndexY - player.getPositionOnTheWorldMap().y
                        + player.getPositionOnTheScreen().y;

                if (player.getPositionOnTheScreen().x > player.getPositionOnTheWorldMap().x) {
                    screenX = worldIndexX;
                }
                if (player.getPositionOnTheScreen().y > player.getPositionOnTheWorldMap().y) {
                    screenY = worldIndexY;
                }
                int rightOffset = this.gamePanel.getScreenWidth() - player.getPositionOnTheScreen().x;
                if (rightOffset > this.map.getWidth()
                        - player.getPositionOnTheWorldMap().x) {
                    screenX = this.gamePanel.getScreenWidth()
                            - (this.map.getWidth() - worldIndexX);
                }
                int bottomOffset = this.gamePanel.getScreenHeight() - player.getPositionOnTheScreen().y;
                if (bottomOffset > this.map.getHeight()
                        - player.getPositionOnTheWorldMap().y) {
                    screenY = this.gamePanel.getScreenHeight()
                            - (this.map.getHeight() - worldIndexY);
                }

                if (worldIndexX + this.gamePanel.getTileSize() > player.getPositionOnTheWorldMap().x
                        - player.getPositionOnTheScreen().x &&
                        worldIndexX - this.gamePanel.getTileSize() < player.getPositionOnTheWorldMap().x
                                + player.getPositionOnTheScreen().x
                        &&
                        worldIndexY + this.gamePanel.getTileSize() > player.getPositionOnTheWorldMap().y
                                - player.getPositionOnTheScreen().y
                        &&
                        worldIndexY - this.gamePanel.getTileSize() < player.getPositionOnTheWorldMap().y
                                + player.getPositionOnTheScreen().y) {

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
        LOGGER.info("Initializing tile manager instance...");
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
        LOGGER.info("Tile manager instance initialized.");
    }
}

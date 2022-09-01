package io.nirahtech.entities.artifacts;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;

import io.nirahtech.entities.apis.GeoLocalizable;
import io.nirahtech.entities.characters.Player;
import io.nirahtech.gui.GamePanel;
import io.nirahtech.runtime.apis.GameProcess;

/**
 * Class that represents an object inside the game environment.
 */
public abstract class SuperObject implements GameProcess, GeoLocalizable {
    protected final GamePanel gamePanel = GamePanel.getInstance();
    protected final BufferedImage image;
    protected final String name;
    protected final Point solidAreaDefaultLocation;
    protected final Point worldLocation;
    protected final Point screenLocation;
    protected final Rectangle solidArea;

    protected boolean isCollision = false;

    /**
     * A Super Object that will be iniside the game environment and which the player
     * will be able to interact with.
     * 
     * @param name  The name of the object.
     * @param image The texture of the object.
     */
    protected SuperObject(final String name, final BufferedImage image) {
        this.name = name;
        this.image = image;
        this.solidAreaDefaultLocation = new Point(0, 0);
        this.solidArea = new Rectangle(
                this.solidAreaDefaultLocation.x,
                this.solidAreaDefaultLocation.y,
                48,
                48);
        this.worldLocation = new Point();
        this.screenLocation = new Point();
    }

    @Override
    public Point getPositionOnTheMap() {
        return this.worldLocation;
    }

    @Override
    public Point getPositionOnTheScreen() {
        return this.screenLocation;
    }

    @Override
    public void update() {

    }

    /**
     * Check if this current object is in the field of vue of the camera.
     * 
     * @param player Get a reference point for camera's screen position.
     * @return {@code true} if it's inside camera's field of vue, else
     *         {@code false}.
     */
    private final boolean isThisInCameraFieldOfViewWithPlayer(final Player player) {
        return (this.getPositionOnTheMap().x + this.gamePanel.getTileSize()) > (player.getWorldX()
                - player.getPositionOnTheScreen().x)
                &&
                (this.getPositionOnTheMap().x - this.gamePanel.getTileSize()) < (player.getWorldX()
                        + player.getPositionOnTheScreen().x)
                &&
                (this.getPositionOnTheMap().y + this.gamePanel.getTileSize()) > (player.getWorldY()
                        - player.getPositionOnTheScreen().y)
                &&
                (this.getPositionOnTheMap().y - this.gamePanel.getTileSize()) < (player.getWorldY()
                        + player.getPositionOnTheScreen().y);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        // Retrieve player reference.
        final Player player = this.gamePanel.getPlayer();

        // Compute screen location (where the object's image will be draw) of this
        // object.
        int screenX = this.getPositionOnTheMap().x - player.getWorldX() + player.getPositionOnTheScreen().x;
        int screenY = this.getPositionOnTheMap().y - player.getWorldY() + player.getPositionOnTheScreen().y;

        // If the object's map location is in the field of view of the camera,
        // draw image.
        if (this.isThisInCameraFieldOfViewWithPlayer(player)) {
            graphics.drawImage(
                    this.image,
                    screenX,
                    screenY,
                    this.gamePanel.getTileSize(),
                    this.gamePanel.getTileSize(),
                    null);
        }

    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

}

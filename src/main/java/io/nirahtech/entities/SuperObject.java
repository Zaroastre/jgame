package io.nirahtech.entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;

import io.nirahtech.runtime.GamePanel;
import io.nirahtech.runtime.GameProcess;

public class SuperObject implements GameProcess {
    protected final BufferedImage image;
    protected final GamePanel gamePanel;
    public boolean isCollision = false;
    protected String name;
    public int worldX, worldY;
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAdreaDefaultX = 0;
    public int solidAdreaDefaultY = 0;

    protected SuperObject(final GamePanel gamePanel, final BufferedImage image) {
        this.gamePanel = gamePanel;
        this.image = image;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void paintComponent(Graphics graphics) {
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

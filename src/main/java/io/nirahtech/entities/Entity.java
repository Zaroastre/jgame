package io.nirahtech.entities;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import io.nirahtech.runtime.AnimationType;
import io.nirahtech.runtime.Direction;

public abstract class Entity {
    protected int worldX;
    protected int worldY;
    protected int screenX;
    protected int screenY;
    protected int speed;
    protected int tileSizeWidth;
    protected int tileSizeHeight;
    protected BufferedImage idle1, idle2;
    protected Direction direction;
    protected int spriteIndexToDisplay = 0;
    protected int spriteCounter = 0;
    protected AnimationType currentAnimation = AnimationType.IDLE;
    protected Map<AnimationType, BufferedImage[]> animations = new HashMap<>();
    protected Rectangle solidArea;
    protected boolean collisionOn = false;

    protected BufferedImage[] loadAnimation(final BufferedImage spritesSheet,
            final int rowIndex,
            final int spriteWidth, final int spriteHeight, final int totalSprites) {
        BufferedImage[] sprites = new BufferedImage[totalSprites];
        for (int columnIndex = 0; columnIndex < totalSprites; columnIndex++) {
            // sprites[(rowIndex * totalSprites) + columnIndex] = spritesSheet.getSubimage(
            sprites[columnIndex] = spritesSheet.getSubimage(
                    columnIndex * spriteWidth,
                    rowIndex * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }
        return sprites;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
}

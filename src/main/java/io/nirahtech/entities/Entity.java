package io.nirahtech.entities;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import io.nirahtech.runtime.AnimationType;
import io.nirahtech.runtime.Direction;

public abstract class Entity implements Moveable, GeoLocalizable {
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
    public int solideAreaDefaultX;
    public int solideAreaDefaultY;
    protected boolean collisionOn = false;
    protected Point mapLocation;
    protected Point screenLocation;

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
        return this.screenLocation.x;
    }

    public int getScreenY() {
        return this.screenLocation.y;
    }

    public int getWorldX() {
        return this.getMapPosition().x;
    }

    public int getWorldY() {
        return this.getMapPosition().y;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setCollisionOn(final boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    @Override
    public void moveDown(final int distance) {
        this.mapLocation.y += distance;
    }

    @Override
    public void moveLeft(final int distance) {
        this.mapLocation.x -= distance;
    }

    @Override
    public void moveRight(final int distance) {
        this.mapLocation.x += distance;
    }

    @Override
    public void moveUp(final int distance) {
        this.mapLocation.y -= distance;
    }

    @Override
    public Point getMapPosition() {
        return this.mapLocation;
    }
}

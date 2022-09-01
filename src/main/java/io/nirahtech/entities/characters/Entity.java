package io.nirahtech.entities.characters;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

import io.nirahtech.entities.apis.GeoLocalizable;
import io.nirahtech.entities.apis.Moveable;
import io.nirahtech.gui.GamePanel;
import io.nirahtech.runtime.AnimationType;
import io.nirahtech.runtime.Direction;

public abstract class Entity implements Moveable, GeoLocalizable {
    protected final GamePanel gamePanel = GamePanel.getInstance();
    protected int speed;
    protected int tileSizeWidth;
    protected int tileSizeHeight;
    protected BufferedImage idle1, idle2;
    protected Direction direction;
    protected int spriteIndexToDisplay = 0;
    protected int spriteCounter = 0;
    protected AnimationType currentAnimation = AnimationType.IDLE;
    protected final EnumMap<AnimationType, BufferedImage[]> animations = new EnumMap<>(AnimationType.class);
    protected int solideAreaDefaultX;
    protected int solideAreaDefaultY;
    protected boolean collisionOn = false;
    protected final Rectangle solidArea;
    protected final Point mapLocation;
    protected final Point screenLocation;

    protected Entity(Point mapLocation, Point screenLocation) {
        this.mapLocation = mapLocation;
        this.screenLocation = screenLocation;
        this.solidArea = new Rectangle(
                this.screenLocation.x,
                this.screenLocation.y,
                this.gamePanel.getTileSize(),
                this.gamePanel.getTileSize());
    }

    

    @Override
    public Point getPositionOnTheScreen() {
        return this.screenLocation;
    }

    @Override
    public Point getPositionOnTheMap() {
        return this.mapLocation;
    }

    public int getWorldX() {
        return this.getPositionOnTheMap().x;
    }

    public int getWorldY() {
        return this.getPositionOnTheMap().y;
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

}

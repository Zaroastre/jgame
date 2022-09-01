package io.nirahtech.entities.characters;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

import io.nirahtech.entities.Entity;
import io.nirahtech.entities.apis.Moveable;
import io.nirahtech.enumerations.AnimationType;
import io.nirahtech.enumerations.Direction;
import io.nirahtech.gui.GamePanel;

public abstract class Character extends Entity implements Moveable {
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

    protected Character(Point mapLocation, Point screenLocation) {
        super(mapLocation, screenLocation);
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

    @Override
    public void moveDown(final int distance) {
        super.getPositionOnTheWorldMap().y += distance;
    }

    @Override
    public void moveLeft(final int distance) {
        super.getPositionOnTheWorldMap().x -= distance;
    }

    @Override
    public void moveRight(final int distance) {
        super.getPositionOnTheWorldMap().x += distance;
    }

    @Override
    public void moveUp(final int distance) {
        super.getPositionOnTheWorldMap().y -= distance;
    }

}

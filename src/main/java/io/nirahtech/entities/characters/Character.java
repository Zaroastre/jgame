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
    protected int moveSpeed;
    protected int tileSizeWidth;
    protected int tileSizeHeight;
    protected BufferedImage idle1, idle2;
    protected Direction direction;
    protected int spriteIndexToDisplay = 0;
    protected int spriteCounter = 0;
    protected AnimationType currentAnimation = AnimationType.IDLE_RIGHT;
    protected final EnumMap<AnimationType, BufferedImage[]> animations = new EnumMap<>(AnimationType.class);
    protected boolean isRunning = false;

    protected Character(Point mapLocation, Point screenLocation) {
        super(mapLocation, screenLocation);
    }

    public void setSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return moveSpeed;
    }

    private final void updateAnimation(final AnimationType animation) {
        if (this.currentAnimation != animation) {
            this.currentAnimation = animation;
            this.spriteIndexToDisplay = 0;
        }
    }

    @Override
    public void moveDown(final int distance) {
        if (this.isRunning) {
            this.updateAnimation(AnimationType.FRONT_RUN_BOTTOM);
        } else {
            this.updateAnimation(AnimationType.FRONT_WALK_BOTTOM);
        }
        super.getPositionOnTheWorldMap().y += distance;
    }

    @Override
    public void moveLeft(final int distance) {
        if (this.isRunning) {
            this.updateAnimation(AnimationType.FRONT_RUN_LEFT);
        } else {
            this.updateAnimation(AnimationType.FRONT_WALK_LEFT);
        }
        super.getPositionOnTheWorldMap().x -= distance;
    }

    @Override
    public void moveRight(final int distance) {
        if (this.isRunning) {
            this.updateAnimation(AnimationType.FRONT_RUN_RIGHT);
        } else {
            this.updateAnimation(AnimationType.FRONT_WALK_RIGHT);
        }
        super.getPositionOnTheWorldMap().x += distance;
    }

    @Override
    public void moveUp(final int distance) {
        if (this.isRunning) {
            this.updateAnimation(AnimationType.FRONT_RUN_TOP);
        } else {
            this.updateAnimation(AnimationType.FRONT_WALK_TOP);
        }
        super.getPositionOnTheWorldMap().y -= distance;
    }

}

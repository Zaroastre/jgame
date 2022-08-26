package io.nirahtech.entities;

import java.io.IOException;
import java.io.InputStream;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import io.nirahtech.runtime.AnimationType;
import io.nirahtech.runtime.Direction;
import io.nirahtech.runtime.GamePanel;
import io.nirahtech.runtime.GameProcess;
import io.nirahtech.runtime.KeyboardHandler;

public class Player extends Entity implements GameProcess {

    private final GamePanel gamePanel;
    private final KeyboardHandler keyboardHandler;

    public Player(final GamePanel gamePanel, final KeyboardHandler keyboardHandler, final int tileSize) {
        this(gamePanel, keyboardHandler);
    }

    public Player(final GamePanel gamePanel, final KeyboardHandler keyboardHandler) {
        super();
        this.gamePanel = gamePanel;
        this.keyboardHandler = keyboardHandler;

        this.screenX = (this.gamePanel.getScreenWidth() / 2) - (this.gamePanel.getTileSize() / 2);
        this.screenY = (this.gamePanel.getScreenHeight() / 2) - (this.gamePanel.getTileSize() / 2);

        this.solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        this.setDefaultValues();
        this.getImage();
    }

    public void setDefaultValues() {
        this.worldX = this.gamePanel.getTileSize() * 320;
        this.worldY = this.gamePanel.getTileSize() * 460;
        this.speed = 10;
        this.tileSizeWidth = 16;
        this.tileSizeHeight = 16;
        this.direction = Direction.LEFT;
    }

    public void getImage() {
        BufferedImage spriteSheet = null;
        try (final InputStream inputStream = Player.class.getClassLoader().getResourceAsStream("Nicolas.png")) {
            spriteSheet = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (spriteSheet != null) {
            final int width = 32;
            final int height = 32;
            final int rows = 0;
            final int cols = 2;
            this.animations.put(AnimationType.IDLE, this.loadAnimation(spriteSheet, rows, width, height, cols));
        }
    }

    @Override
    public void update() {
        Direction previousDirection = this.direction;
        boolean isKeyPressed = false;
        if (this.keyboardHandler.isUpPressed()) {
            this.direction = Direction.UP;
            isKeyPressed = true;
        } else if (this.keyboardHandler.isDownPressed()) {
            this.direction = Direction.DOWN;
            isKeyPressed = true;
        }
        if (this.keyboardHandler.isLeftPressed()) {
            this.direction = Direction.LEFT;
            isKeyPressed = true;
        } else if (this.keyboardHandler.isRightPressed()) {
            this.direction = Direction.RIGHT;
            isKeyPressed = true;
        }

        this.collisionOn = false;
        this.gamePanel.getCollisionChecker().check(this);
        if (isKeyPressed) {
            if (this.collisionOn == false) {
                if (this.direction == Direction.RIGHT) {
                    this.worldX += this.speed;
                } else if (this.direction == Direction.LEFT) {
                    this.worldX -= this.speed;
                }
                if (this.direction == Direction.DOWN) {
                    this.worldY += this.speed;
                } else if (this.direction == Direction.UP) {
                    this.worldY -= this.speed;
                }
            }
        }

        this.spriteCounter++;
        if (this.spriteCounter > 10)

        {
            if (this.direction != previousDirection) {
                this.spriteIndexToDisplay = 0;
            } else {
                this.spriteIndexToDisplay++;
            }
            if (this.spriteIndexToDisplay >= this.animations.get(this.currentAnimation).length) {
                this.spriteIndexToDisplay = 0;
            }
            this.spriteCounter = 0;
        }

    }

    @Override
    public void paintComponent(Graphics graphics2D) {
        final BufferedImage image = this.animations.get(this.currentAnimation)[this.spriteIndexToDisplay];
        graphics2D.drawImage(image, this.screenX, this.screenY, this.tileSizeWidth, this.tileSizeHeight, null);

    }

}

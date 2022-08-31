package io.nirahtech.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import io.nirahtech.runtime.AnimationType;
import io.nirahtech.runtime.Direction;
import io.nirahtech.runtime.GamePanel;
import io.nirahtech.runtime.GameProcess;
import io.nirahtech.runtime.Initializable;
import io.nirahtech.runtime.KeyboardHandler;

public class Player extends Entity implements GameProcess, Initializable {

    private static Player instance;

    public static final Player getInstance() {
        if (Player.instance == null) {
            Player.instance = new Player();
        }
        return Player.instance;
    }

    private GamePanel gamePanel;
    private KeyboardHandler keyboardHandler;

    private Player() {
        super();

    }

    public void setDefaultValues() {
        this.mapLocation = new Point();
        this.mapLocation.x = this.gamePanel.getTileSize() * 417;
        this.mapLocation.y = this.gamePanel.getTileSize() * 670;
        this.speed = this.gamePanel.getTileManager().getWorldMap().getWidth() / 1920;
        this.tileSizeWidth = this.gamePanel.getTileSize();
        this.tileSizeHeight = this.gamePanel.getTileSize();
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
        this.gamePanel.getCollisionChecker().checkTile(this);
        Optional<Integer> indexOfSuperObjectTouched = this.gamePanel.getCollisionChecker().checkSuperObject(this);
        if (indexOfSuperObjectTouched.isPresent()) {
            dropSuperObject(indexOfSuperObjectTouched.get());
        }
        if (isKeyPressed) {
            if (this.collisionOn == false) {
                if (this.direction == Direction.RIGHT) {
                    this.moveRight(this.speed);
                } else if (this.direction == Direction.LEFT) {
                    this.moveLeft(this.speed);
                }
                if (this.direction == Direction.DOWN) {
                    this.moveDown(this.speed);
                } else if (this.direction == Direction.UP) {
                    this.moveUp(this.speed);
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

    private void dropSuperObject(final int index) {
        if (index < this.gamePanel.objects.length) {
            this.gamePanel.objects[index] = null;
        }
    }

    @Override
    public void paintComponent(Graphics graphics2D) {
        final BufferedImage image = this.animations.get(this.currentAnimation)[this.spriteIndexToDisplay];
        graphics2D.drawImage(image, this.screenLocation.x, this.screenLocation.y, this.tileSizeWidth,
                this.tileSizeHeight, null);
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(this.solidArea.x, this.solidArea.y, this.solidArea.width, this.solidArea.height);
    }

    @Override
    public void initialize(ResourceBundle configuration) {
        this.gamePanel = GamePanel.getInstance();
        this.screenLocation = new Point();
        this.screenLocation.x = (this.gamePanel.getScreenWidth() / 2) - (this.gamePanel.getTileSize() / 2);
        this.screenLocation.y = (this.gamePanel.getScreenHeight() / 2) - (this.gamePanel.getTileSize() / 2);

        this.solidArea = new Rectangle();
        this.solidArea.x = this.screenLocation.x + 4;
        this.solidArea.y = this.screenLocation.y + 8;
        this.solidArea.width = 8;
        this.solidArea.height = 8;
        this.solideAreaDefaultX = this.solidArea.x;
        this.solideAreaDefaultY = this.solidArea.y;

        this.keyboardHandler = KeyboardHandler.getInstance();
        this.setDefaultValues();
        this.getImage();
    }

}

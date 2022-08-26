package io.nirahtech.runtime;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import io.nirahtech.entities.Player;
import io.nirahtech.tile.TileManager;

public final class GamePanel extends JPanel implements Runnable, GameProcess {

    // Screen Settings
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = this.originalTileSize * this.scale;
    private final int maxScreenColumns = (int) screenSize.getWidth() / this.tileSize;
    private final int maxScreenRows = (int) screenSize.getHeight() / this.tileSize;;
    private final int screenWidth = this.tileSize * this.maxScreenColumns;
    private final int screenHeight = this.tileSize * this.maxScreenRows;

    // Engine Settings
    private final int fps = 60;
    private final KeyboardHandler keyboardHandler = new KeyboardHandler();
    private final TileManager tileManager = new TileManager(this, this.tileSize);
    private final CollisionChecker collisionChecker = new CollisionChecker(this);
    private final Player player = new Player(this, this.keyboardHandler);
    private final Thread gameThread = new Thread(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyboardHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        this.gameThread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        final double drawInterval = 1_000_000_000 / this.fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                this.update();
                this.repaint();
                delta--;
            }

        }
    }

    @Override
    public void update() {
        this.player.update();

    }

    @Override
    public void paintComponent(final Graphics graphics) {
        // TODO Auto-generated method stub
        super.paintComponent(graphics);
        final Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.paintComponent(graphics2D);
        this.player.paintComponent(graphics2D);
        graphics2D.dispose();
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getMaxScreenRows() {
        return maxScreenRows;
    }

    public int getMaxScreenColumns() {
        return maxScreenColumns;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}

package io.nirahtech.runtime;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;

import io.nirahtech.entities.Player;
import io.nirahtech.entities.SuperObject;
import io.nirahtech.sound.Sound;
import io.nirahtech.tile.TileManager;

public final class GamePanel extends JPanel implements Runnable, GameProcess, Zoomable, Initializable {

    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getSimpleName());
    private static GamePanel instance;

    public static final GamePanel getInstance() {
        LOGGER.info("Calling unique instance of game panel");
        if (GamePanel.instance == null) {
            GamePanel.instance = new GamePanel();
        }
        return GamePanel.instance;
    }

    // Screen Settings
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int originalTileSize = 16;
    private int scale = 3;
    private int tileSize = this.originalTileSize * this.scale;
    private final int maxScreenColumns = (int) screenSize.getWidth() / this.tileSize;
    private final int maxScreenRows = (int) screenSize.getHeight() / this.tileSize;;
    private final int screenWidth = this.tileSize * this.maxScreenColumns;
    private final int screenHeight = this.tileSize * this.maxScreenRows;

    // Engine Settings
    private final int fps = 60;
    private Player player;
    private final Thread gameThread = new Thread(this);
    private final UI ui = new UI(this);
    private final Sound sound = new Sound();
    public SuperObject[] objects = new SuperObject[10];
    private JLabel locationLabel = new JLabel();
    private KeyboardHandler keyboardHandler;
    private MouseWheelHandler mouseHandler;
    private TileManager tileManager;
    private CollisionChecker collisionChecker;
    boolean isPlayState = true;

    @Override
    public void zoom(int ratio) {
        int oldWorldWidth = this.tileSize * this.tileManager.getWorldMap().getOriginalWidth();
        this.scale += ratio;
        this.updateTileSize();
        int newWorldWidth = this.tileSize * this.tileManager.getWorldMap().getOriginalWidth();
        double multiplier = (double) newWorldWidth / oldWorldWidth;
        player.setSpeed(newWorldWidth / 1920);
        player.getMapPosition().x = (int) (player.getWorldX() * multiplier);
        player.getMapPosition().y = (int) (player.getWorldY() * multiplier);

    }

    @Override
    public void zoomOut() {
        if (this.scale > 2) {
            this.zoom(-1);
        }
    }

    @Override
    public void zoomIn() {
        if (this.scale < 10) {
            this.zoom(1);
        }
    }

    private void updateTileSize() {
        this.tileSize = this.originalTileSize * this.scale;
    }

    private GamePanel() {
        LOGGER.info("Building game panel");
    }

    public void startGameThread() {
        LOGGER.info("Game thread will start...");
        this.gameThread.start();
        this.sound.play();
        this.sound.loop();
    }

    public int getOriginalTileSize() {
        return originalTileSize;
    }

    @Override
    public void run() {

        LOGGER.info("Game thread is started");
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
        if (this.isPlayState) {
            this.player.update();
        }

    }

    @Override
    public void paintComponent(final Graphics graphics) {
        if (this.isPlayState) {
            // TODO Auto-generated method stub
            super.paintComponent(graphics);
            final Graphics2D graphics2D = (Graphics2D) graphics;
            tileManager.paintComponent(graphics2D);
            for (SuperObject superObject : objects) {
                if (superObject != null) {
                    superObject.paintComponent(graphics2D);
                }
            }
            this.player.paintComponent(graphics2D);
            this.ui.paintComponent(graphics2D);
            graphics2D.dispose();
        }
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

    public int getScale() {
        return scale;
    }

    @Override
    public void initialize(ResourceBundle configuration) {
        LOGGER.info("Initializing game panel instance...");
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.locationLabel.setLayout(new FlowLayout());
        this.locationLabel.setLocation(100, 100);
        this.add(this.locationLabel);

        this.keyboardHandler = KeyboardHandler.getInstance();
        this.mouseHandler = MouseWheelHandler.getInstance();
        this.tileManager = TileManager.getInstance();
        this.collisionChecker = CollisionChecker.getInstance();
        this.player = Player.getInstance();

        this.tileManager.initialize(configuration);
        this.player.initialize(configuration);
        this.collisionChecker.initialize(configuration);
        this.mouseHandler.initialize(configuration);
        this.keyboardHandler.initialize(configuration);
        LOGGER.info("Game panel instance initialized.");
    }
}

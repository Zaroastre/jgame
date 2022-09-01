package io.nirahtech.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import io.nirahtech.entities.artifacts.SuperObject;
import io.nirahtech.entities.characters.Player;
import io.nirahtech.enumerations.GameStep;
import io.nirahtech.runtime.CollisionChecker;
import io.nirahtech.runtime.Initializable;
import io.nirahtech.runtime.UI;
import io.nirahtech.runtime.apis.GameProcess;
import io.nirahtech.runtime.apis.Zoomable;
import io.nirahtech.runtime.io.handlers.KeyboardHandler;
import io.nirahtech.runtime.io.handlers.MouseWheelHandler;
import io.nirahtech.sound.Sound;
import io.nirahtech.tile.TileManager;

public final class GamePanel extends JPanel implements Runnable, GameProcess, Zoomable, Initializable {

    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getSimpleName());
    private static GamePanel instance;
    private static JFrame window;

    public static final GamePanel getInstance(JFrame window) {
        LOGGER.info("Calling unique instance of game panel");
        if (GamePanel.instance == null) {
            GamePanel.instance = new GamePanel();
            GamePanel.window = window;
        }
        return GamePanel.instance;
    }

    public static final GamePanel getInstance() {
        LOGGER.info("Calling unique instance of game panel");
        return GamePanel.instance;
    }

    // Screen Settings
    private int originalTileSize = 16;
    private int scale = 3;
    private int tileSize = this.originalTileSize * this.scale;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int maxScreenColumns = (int) screenSize.getWidth() / this.tileSize;
    private final int maxScreenRows = (int) screenSize.getHeight() / this.tileSize;
    private int screenWidth = this.tileSize * this.maxScreenColumns;
    private int screenHeight = this.tileSize * this.maxScreenRows;
    private int fps = 60;

    // Engine Settings
    private Player player;
    private final Thread gameThread = new Thread(this);
    private final UI ui = new UI(this);
    private final Sound sound;
    public SuperObject[] objects = new SuperObject[10];
    private JLabel locationLabel = new JLabel();
    private KeyboardHandler keyboardHandler;
    private MouseWheelHandler mouseHandler;
    private TileManager tileManager;
    private CollisionChecker collisionChecker;
    private GameStep gameStep = GameStep.IN_GAME;
    private BufferedImage tempScreen;
    private Graphics2D canvas;

    @Override
    public void zoom(int ratio) {
        int oldWorldWidth = this.tileSize * this.tileManager.getWorldMap().getOriginalWidth();
        this.scale += ratio;
        this.updateTileSize();
        int newWorldWidth = this.tileSize * this.tileManager.getWorldMap().getOriginalWidth();
        double multiplier = (double) newWorldWidth / oldWorldWidth;
        player.setSpeed(newWorldWidth / (int) screenSize.getWidth());
        player.getPositionOnTheWorldMap().x = (int) (player.getPositionOnTheWorldMap().x * multiplier);
        player.getPositionOnTheWorldMap().y = (int) (player.getPositionOnTheWorldMap().y * multiplier);

    }

    public Sound getSound() {
        return sound;
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
        this.sound = new Sound("main-ost.wav");
    }

    public void startGameThread() {

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = graphicsEnvironment.getDefaultScreenDevice();
        screen.setFullScreenWindow(GamePanel.window);
        this.screenWidth = GamePanel.window.getWidth();
        this.screenHeight = GamePanel.window.getHeight();
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
                // this.repaint();
                this.createBufferedImageToDraw();
                this.drawBufferedImageToScreen();
                delta--;
            }

        }
    }

    @Override
    public void update() {
        switch (this.gameStep) {
            case MAIN_MENU:
                break;
            case IN_GAME:
                this.player.update();
                if (!this.sound.isPlaying() && this.sound.isPaused()) {
                    this.sound.play();
                }
                break;
            default:
                break;
        }

    }

    public void drawBufferedImageToScreen() {
        Graphics graphics = this.getGraphics();
        graphics.drawImage(this.tempScreen, 0, 0, this.screenWidth, this.screenHeight, null);
        graphics.dispose();
    }

    public void createBufferedImageToDraw() {
        switch (this.gameStep) {
            case MAIN_MENU:
                break;
            case IN_GAME:
                tileManager.paintComponent(this.canvas);
                for (SuperObject superObject : objects) {
                    if (superObject != null) {
                        superObject.paintComponent(this.canvas);
                    }
                }
                this.player.paintComponent(this.canvas);
                this.ui.paintComponent(this.canvas);

                break;
            case PAUSED:
                String pauseText = "PAUSE";
                Rectangle pausePanel = new Rectangle();
                pausePanel.x = 0;
                pausePanel.height = 200;
                pausePanel.y = (this.screenHeight / 2) - (pausePanel.height / 2);
                pausePanel.width = this.screenWidth;

                this.canvas.setFont(UI.font);
                this.canvas.setColor(Color.BLACK);
                this.canvas.fillRect(pausePanel.x, pausePanel.y, pausePanel.width, pausePanel.height);
                this.canvas.setColor(Color.RED);
                this.canvas.drawRect(pausePanel.x, pausePanel.y, pausePanel.width, pausePanel.height);
                this.canvas.setColor(Color.WHITE);
                int length = (int) this.canvas.getFontMetrics().getStringBounds(pauseText, this.canvas).getWidth();
                this.canvas.drawString(pauseText, (this.screenWidth / 2) - (length / 2), (this.screenHeight / 2));
                if (this.sound.isPlaying() && !this.sound.isPaused()) {
                    this.sound.pause();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void paintComponent(final Graphics graphics) {
        final Graphics2D graphics2D = (Graphics2D) graphics;

        super.paintComponent(graphics);
        switch (this.gameStep) {
            case IN_GAME:
                tileManager.paintComponent(graphics2D);
                for (SuperObject superObject : objects) {
                    if (superObject != null) {
                        superObject.paintComponent(graphics2D);
                    }
                }
                this.player.paintComponent(graphics2D);
                this.ui.paintComponent(graphics2D);

                break;
            case PAUSED:
                String pauseText = "PAUSE";
                Rectangle pausePanel = new Rectangle();
                pausePanel.x = 0;
                pausePanel.height = 200;
                pausePanel.y = (this.screenHeight / 2) - (pausePanel.height / 2);
                pausePanel.width = this.screenWidth;

                graphics2D.setFont(UI.font);
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(pausePanel.x, pausePanel.y, pausePanel.width, pausePanel.height);
                graphics2D.setColor(Color.RED);
                graphics2D.drawRect(pausePanel.x, pausePanel.y, pausePanel.width, pausePanel.height);
                graphics2D.setColor(Color.WHITE);
                int length = (int) graphics2D.getFontMetrics().getStringBounds(pauseText, graphics2D).getWidth();
                graphics2D.drawString(pauseText, (this.screenWidth / 2) - (length / 2), (this.screenHeight / 2));
                if (this.sound.isPlaying() && !this.sound.isPaused()) {
                    this.sound.pause();
                }
                break;
            default:
                break;
        }

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

    public int getScale() {
        return scale;
    }

    public GameStep getGameStep() {
        return this.gameStep;
    }

    public void setGameStep(final GameStep gameStep) {
        this.gameStep = gameStep;
    }

    // getW

    @Override
    public void initialize(ResourceBundle configuration) {
        LOGGER.info("Initializing game panel instance...");
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.tempScreen = new BufferedImage(this.screenWidth, this.screenHeight, BufferedImage.TYPE_INT_ARGB);
        this.canvas = (Graphics2D) this.tempScreen.getGraphics();

        this.fps = Integer.parseInt(configuration.getString("fps"));
        this.originalTileSize = Integer.parseInt(configuration.getString("tileSize"));
        this.scale = Integer.parseInt(configuration.getString("defaultScale"));
        this.updateTileSize();

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

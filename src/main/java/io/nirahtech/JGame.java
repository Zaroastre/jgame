package io.nirahtech;

import java.awt.Cursor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;

import io.nirahtech.gui.GamePanel;
import io.nirahtech.runtime.io.handlers.KeyboardHandler;
import io.nirahtech.runtime.io.handlers.MouseWheelHandler;

/**
 * Hello world!
 *
 */
public class JGame {
    private static Logger LOGGER;

    private static final void initializeLogger() {
        InputStream stream = JGame.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER = Logger.getLogger(JGame.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] commandLineArguments) {
        initializeLogger();
        LOGGER.info("Video game application is starting...");
        LOGGER.info("Loading resource bundle...");
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(JGame.class.getSimpleName().toLowerCase(),
                Locale.getDefault());

        LOGGER.info("Creating window...");
        final JFrame window = new JFrame();
        window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.setFocusable(true);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle(resourceBundle.getString("title"));

        LOGGER.info("Creating game panel...");
        final GamePanel gamePanel = GamePanel.getInstance(window);

        LOGGER.info("Attaching event handler for keyboard...");
        window.addKeyListener(KeyboardHandler.getInstance());
        LOGGER.info("Attaching event handler for mouse wheel...");
        window.addMouseWheelListener(MouseWheelHandler.getInstance());
        LOGGER.info("Initializing game panel...");
        gamePanel.initialize(resourceBundle);
        LOGGER.info("Adding game panel to window...");
        window.add(gamePanel);
        window.pack();
        LOGGER.info("Starting video game engine...");
        window.setCursor(Cursor.getDefaultCursor());
        gamePanel.startGameThread();
    }
}

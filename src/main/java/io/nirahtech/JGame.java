package io.nirahtech;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import io.nirahtech.runtime.GamePanel;

/**
 * Hello world!
 *
 */
public class JGame {
    public static void main(String[] commandLineArguments) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(JGame.class.getSimpleName().toLowerCase(),
                Locale.getDefault());

        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setTitle(resourceBundle.getString("title"));

        final GamePanel gamePanel = GamePanel.getInstance();
        gamePanel.initialize(resourceBundle);
        window.add(gamePanel);
        window.pack();
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

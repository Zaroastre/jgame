package io.nirahtech;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import io.nirahtech.runtime.GamePanel;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("J4Game");

        final GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // window.setUndecorated(true);
        // window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        gamePanel.startGameThread();
    }
}

package io.nirahtech.runtime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI implements GameProcess {
    private final GamePanel gamePanel;
    private Font font;

    public UI(final GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.font = new Font("Arial", Font.PLAIN, 20);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void paintComponent(final Graphics graphics) {
        graphics.setFont(this.font);
        graphics.setColor(Color.RED);
        String playerWorldMapLocationText = String.format("Player Location (WorldMap): (x: %s, y: %s",
                this.gamePanel.getPlayer().getMapPosition().x,
                this.gamePanel.getPlayer().getMapPosition().y);
        String playerScreenLocationText = String.format("Player Location (Screen): (x: %s, y: %s)",
                this.gamePanel.getPlayer().getScreenX(),
                this.gamePanel.getPlayer().getScreenY());
        String mapDimensions = String.format("Map: (x: %s, y: %s) - With tile size (): (x: %s, y: %s)",
                this.gamePanel.getTileManager().getWorldMap().getOriginalWidth(),
                this.gamePanel.getTileManager().getWorldMap().getOriginalHeight(),
                this.gamePanel.getTileManager().getWorldMap().getWidth(),
                this.gamePanel.getTileManager().getWorldMap().getHeight());
        graphics.drawString(playerWorldMapLocationText, 0, 40);
        graphics.drawString(playerScreenLocationText, 0, 60);
        graphics.drawString(mapDimensions, 0, 80);
    }

}

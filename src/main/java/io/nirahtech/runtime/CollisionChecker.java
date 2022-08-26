package io.nirahtech.runtime;

import io.nirahtech.entities.Entity;
import io.nirahtech.tile.Tile;

public class CollisionChecker {
    private final GamePanel gamePanel;

    public CollisionChecker(final GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void check(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + (entity.getSolidArea().x + entity.getSolidArea().width);
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + (entity.getSolidArea().y + entity.getSolidArea().height);

        int entityLeftColumn = entityLeftWorldX / this.gamePanel.getTileSize();
        int entityRightColumn = entityRightWorldX / this.gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / this.gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / this.gamePanel.getTileSize();

        Tile tileNum1 = null, tileNum2 = null;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / this.gamePanel.getTileSize();
                tileNum1 = this.gamePanel.getTileManager().getMap()[entityLeftColumn][entityTopRow];
                tileNum2 = this.gamePanel.getTileManager().getMap()[entityRightColumn][entityTopRow];
                break;
            case RIGHT:

                break;
            case DOWN:

                break;
            case LEFT:

                break;
        }
        if (tileNum1 != null && tileNum2 != null) {
            System.out.println(String.format("Tile Top-Left: %s | Tile Top-Right: %s", tileNum1, tileNum2));
            entity.setCollisionOn(tileNum1.isCollision || tileNum2.isCollision);
        }
    }
}

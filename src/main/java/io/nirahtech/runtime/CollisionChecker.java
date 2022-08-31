package io.nirahtech.runtime;

import java.util.Optional;
import java.util.ResourceBundle;

import io.nirahtech.entities.Entity;
import io.nirahtech.entities.Player;
import io.nirahtech.entities.SuperObject;
import io.nirahtech.tile.Tile;

public class CollisionChecker implements Initializable {
    private static CollisionChecker instance;

    public static final CollisionChecker getInstance() {
        if (CollisionChecker.instance == null) {
            CollisionChecker.instance = new CollisionChecker();
        }
        return CollisionChecker.instance;
    }

    private GamePanel gamePanel;

    private CollisionChecker() {

    }

    public void checkTile(final Entity entity) {

        // Set rectangle perimeter for collision
        final int entityLeftWorldX = entity.getMapPosition().x + entity.getSolidArea().x;
        final int entityRightWorldX = entity.getMapPosition().x
                + (entity.getSolidArea().x + entity.getSolidArea().width);
        final int entityTopWorldY = entity.getMapPosition().y + entity.getSolidArea().y;
        final int entityBottomWorldY = entity.getMapPosition().y
                + (entity.getSolidArea().y + entity.getSolidArea().height);

        int entityLeftColumn = entityLeftWorldX / this.gamePanel.getTileSize();
        int entityRightColumn = entityRightWorldX / this.gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / this.gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / this.gamePanel.getTileSize();

        Tile tileNum1 = null, tileNum2 = null;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / this.gamePanel.getTileSize();
                if (this.gamePanel.getPlayer().getMapPosition().y > 0) {
                    tileNum1 = this.gamePanel.getTileManager().getWorldMap().getTile(entityLeftColumn, entityTopRow);
                    tileNum2 = this.gamePanel.getTileManager().getWorldMap().getTile(entityRightColumn, entityTopRow);
                } else {
                    entity.setCollisionOn(true);
                }
                break;
            case RIGHT:
                entityRightColumn = (entityRightWorldX + entity.getSpeed()) / this.gamePanel.getTileSize();
                if (this.gamePanel.getPlayer().getMapPosition().x < (this.gamePanel.getTileManager().getWorldMap()
                        .getOriginalWidth() * this.gamePanel.getTileSize())) {
                    tileNum1 = this.gamePanel.getTileManager().getWorldMap().getTile(entityRightColumn, entityTopRow);
                    tileNum2 = this.gamePanel.getTileManager().getWorldMap().getTile(entityRightColumn,
                            entityBottomRow);
                } else {
                    entity.setCollisionOn(true);
                }
                break;
            case DOWN:
                if (this.gamePanel.getPlayer().getMapPosition().y < (this.gamePanel.getTileManager().getWorldMap()
                        .getOriginalHeight() * this.gamePanel.getTileSize())) {
                    tileNum1 = this.gamePanel.getTileManager().getWorldMap().getTile(entityLeftColumn, entityBottomRow);
                    tileNum2 = this.gamePanel.getTileManager().getWorldMap().getTile(entityRightColumn,
                            entityBottomRow);
                } else {
                    entity.setCollisionOn(true);
                }
                break;
            case LEFT:
                entityLeftColumn = (entityRightWorldX - entity.getSpeed()) / this.gamePanel.getTileSize();
                if (this.gamePanel.getPlayer().getMapPosition().x > 0) {
                    tileNum1 = this.gamePanel.getTileManager().getWorldMap().getTile(entityLeftColumn, entityTopRow);
                    tileNum2 = this.gamePanel.getTileManager().getWorldMap().getTile(entityLeftColumn,
                            entityBottomRow);
                } else {
                    entity.setCollisionOn(true);
                }
                break;
        }
        if (tileNum1 != null && tileNum2 != null) {
            entity.setCollisionOn(tileNum1.isCollision || tileNum2.isCollision);
        }
    }

    public Optional<Integer> checkSuperObject(final Entity entity) {
        Optional<Integer> optionalIndex = Optional.empty();
        for (int i = 0; i < this.gamePanel.objects.length; i++) {
            SuperObject superObject = this.gamePanel.objects[i];
            if (superObject != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
                superObject.getSolidArea().x = superObject.worldX + superObject.getSolidArea().x;
                superObject.getSolidArea().y = superObject.worldY + superObject.getSolidArea().y;

                switch (entity.getDirection()) {
                    case UP:
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObject.getSolidArea())) {
                            if (superObject.isCollision) {
                                entity.setCollisionOn(true);
                            }
                            if (entity instanceof Player) {
                                optionalIndex = Optional.of(i);
                            }
                        }
                        break;
                    case RIGHT:

                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObject.getSolidArea())) {
                            if (superObject.isCollision) {
                                entity.setCollisionOn(true);
                            }
                            if (entity instanceof Player) {
                                optionalIndex = Optional.of(i);
                            }
                        }
                        break;
                    case DOWN:

                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObject.getSolidArea())) {
                            if (superObject.isCollision) {
                                entity.setCollisionOn(true);
                            }
                            if (entity instanceof Player) {
                                optionalIndex = Optional.of(i);
                            }
                        }
                        break;
                    case LEFT:
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(superObject.getSolidArea())) {
                            if (superObject.isCollision) {
                                entity.setCollisionOn(true);
                            }
                            if (entity instanceof Player) {
                                optionalIndex = Optional.of(i);
                            }
                        }
                        break;
                    default:
                        break;
                }
                entity.getSolidArea().x = entity.solideAreaDefaultX;
                entity.getSolidArea().y = entity.solideAreaDefaultY;
                superObject.getSolidArea().x = superObject.solidAdreaDefaultX;
                superObject.getSolidArea().y = superObject.solidAdreaDefaultY;
            }
        }
        return optionalIndex;
    }

    @Override
    public void initialize(ResourceBundle configuration) {
        this.gamePanel = GamePanel.getInstance();

    }
}

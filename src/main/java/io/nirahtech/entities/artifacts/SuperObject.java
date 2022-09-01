package io.nirahtech.entities.artifacts;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import io.nirahtech.entities.Entity;
import io.nirahtech.entities.characters.Player;
import io.nirahtech.runtime.apis.GameProcess;

/**
 * Class that represents an object inside the game environment.
 */
public abstract class SuperObject extends Entity implements GameProcess {
        protected final BufferedImage image;
        protected final String name;

        /**
         * A Super Object that will be iniside the game environment and which the player
         * will be able to interact with.
         * 
         * @param name  The name of the object.
         * @param image The texture of the object.
         */
        protected SuperObject(final String name, final BufferedImage image) {
                super(new Point(), new Point());
                this.name = name;
                this.image = image;
        }

        @Override
        public void update() {

        }

        /**
         * Check if this current object is in the field of vue of the camera.
         * 
         * @param player Get a reference point for camera's screen position.
         * @return {@code true} if it's inside camera's field of vue, else
         *         {@code false}.
         */
        private final boolean isThisInCameraFieldOfViewWithPlayer(final Player player) {
                return (this.getPositionOnTheWorldMap().x
                                + Entity.GAME_PANEL.getTileSize()) > (player.getPositionOnTheWorldMap().x
                                                - player.getPositionOnTheScreen().x)
                                &&
                                (this.getPositionOnTheWorldMap().x
                                                - Entity.GAME_PANEL
                                                                .getTileSize()) < (player.getPositionOnTheWorldMap().x
                                                                                + player.getPositionOnTheScreen().x)
                                &&
                                (this.getPositionOnTheWorldMap().y
                                                + Entity.GAME_PANEL
                                                                .getTileSize()) > (player.getPositionOnTheWorldMap().y
                                                                                - player.getPositionOnTheScreen().y)
                                &&
                                (this.getPositionOnTheWorldMap().y
                                                - Entity.GAME_PANEL
                                                                .getTileSize()) < (player.getPositionOnTheWorldMap().y
                                                                                + player.getPositionOnTheScreen().y);
        }

        @Override
        public void paintComponent(Graphics graphics) {
                // Retrieve player reference.
                final Player player = Entity.GAME_PANEL.getPlayer();

                // Compute screen location (where the object's image will be draw) of this
                // object.
                int screenX = this.getPositionOnTheWorldMap().x - player.getPositionOnTheWorldMap().x
                                + player.getPositionOnTheScreen().x;
                int screenY = this.getPositionOnTheWorldMap().y - player.getPositionOnTheWorldMap().y
                                + player.getPositionOnTheScreen().y;

                // If the object's map location is in the field of view of the camera,
                // draw image.
                if (this.isThisInCameraFieldOfViewWithPlayer(player)) {
                        graphics.drawImage(
                                        this.image,
                                        screenX,
                                        screenY,
                                        Entity.GAME_PANEL.getTileSize(),
                                        Entity.GAME_PANEL.getTileSize(),
                                        null);
                }

        }

}

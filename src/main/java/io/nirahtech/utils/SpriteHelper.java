package io.nirahtech.utils;

import java.awt.image.BufferedImage;

public final class SpriteHelper {

    private SpriteHelper() {

    }

    /**
     * Load a sprite animation given the spritesheet, the number of the line where
     * is located the sprites, the size of the sprite (width, height) and the number
     * of srpite that composed the animation.
     * 
     * @param spritesSheet The image that contains all the sprites.
     * @param rowIndex     The number of the line of the spritesheet where is
     *                     located the animation to load.
     * @param spriteWidth  The width of one sprite.
     * @param spriteHeight The height of one sprite.
     * @param totalSprites The number of sprites to load for animation creation.
     * @return
     */
    public static final BufferedImage[] loadSpriteAnimation(
            final BufferedImage spritesSheet,
            final int rowIndex,
            final int spriteWidth,
            final int spriteHeight,
            final int totalSprites) {
        final BufferedImage[] sprites = new BufferedImage[totalSprites];
        for (int columnIndex = 0; columnIndex < totalSprites; columnIndex++) {
            // sprites[(rowIndex * totalSprites) + columnIndex] = spritesSheet.getSubimage(
            sprites[columnIndex] = spritesSheet.getSubimage(
                    columnIndex * spriteWidth,
                    rowIndex * spriteHeight,
                    spriteWidth,
                    spriteHeight);
        }
        return sprites;
    }
}

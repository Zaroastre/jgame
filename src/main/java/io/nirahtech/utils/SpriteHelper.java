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
     * @param columnIndex  The number of the column of the spritesheet where is
     *                     located the animation to load.
     * @param spriteWidth  The width of one sprite.
     * @param spriteHeight The height of one sprite.
     * @param totalSprites The number of sprites to load for animation creation.
     * @param orientation  The orientation to read the spritesheet. Left to right or
     *                     top to bottom.
     * @return
     */
    public static final BufferedImage[] loadSpriteAnimation(
            final BufferedImage spritesSheet,
            final int rowIndex,
            final int columnIndex,
            final int spriteWidth,
            final int spriteHeight,
            final int totalSprites,
            final SpriteSheetOrientation orientation) {
        final BufferedImage[] sprites = new BufferedImage[totalSprites];
        switch (orientation) {
            case HORIZONTAL:
                for (int index = 0; index < totalSprites; index++) {
                    sprites[index] = spritesSheet.getSubimage(
                            index * spriteWidth,
                            rowIndex * spriteHeight,
                            spriteWidth,
                            spriteHeight);
                }
                break;

            case VERTICAL:
                for (int index = 0; index < totalSprites; index++) {
                    sprites[index] = spritesSheet.getSubimage(
                            columnIndex * spriteWidth,
                            index * spriteHeight,
                            spriteWidth,
                            spriteHeight);
                }
                break;
            default:
                break;
        }
        return sprites;
    }
}

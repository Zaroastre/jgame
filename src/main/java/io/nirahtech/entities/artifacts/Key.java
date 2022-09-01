package io.nirahtech.entities.artifacts;

import java.awt.image.BufferedImage;

/**
 * 
 */
public class Key extends SuperObject {

    /**
     * An object that represents a key to unlock all kinds of locks.
     * 
     * @param image The texture of the key.
     */
    public Key(final BufferedImage image) {
        super("key", image);
    }

}

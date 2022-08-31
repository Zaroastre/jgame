package io.nirahtech.runtime;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class ImageScaler {
    public static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D graphics = scaledImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, width, height, null);
        graphics.dispose();
        return scaledImage;
    }
}

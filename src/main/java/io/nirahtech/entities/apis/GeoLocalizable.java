package io.nirahtech.entities.apis;

import java.awt.Point;

/**
 * Intrface that expose API for a Geo Localizable object.
 */
public interface GeoLocalizable {

    /**
     * Get the position on the world map givin a 2D point (x, y).
     * 
     * @return The current position on the world map.
     */
    Point getPositionOnTheMap();

    /**
     * Get the position on the screen givin a 2D point (x, y).
     * 
     * @return The current position on the screen.
     */
    Point getPositionOnTheScreen();
}

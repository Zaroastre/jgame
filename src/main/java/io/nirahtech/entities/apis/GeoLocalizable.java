package io.nirahtech.entities.apis;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Intrface that expose API for a Geo Localizable object.
 */
public interface GeoLocalizable {

    /**
     * Get the position on the world map givin a 2D point (x, y).
     * 
     * @return The current position on the world map.
     */
    Point getPositionOnTheWorldMap();

    /**
     * Get the position on the screen givin a 2D point (x, y).
     * 
     * @return The current position on the screen.
     */
    Point getPositionOnTheScreen();

    Point getSolidAreaDefaultLocation();

    Rectangle getSolidArea();
}

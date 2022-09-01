package io.nirahtech.entities.apis;

/**
 * Intrface that expose API for a Geo Moveable object.
 */
public interface Moveable {

    /**
     * Move to the top.
     * 
     * @param distance The distance to travel.
     */
    void moveUp(final int distance);

    /**
     * Move to the bottom.
     * 
     * @param distance The distance to travel.
     */
    void moveDown(final int distance);

    /**
     * Move to the left.
     * 
     * @param distance The distance to travel.
     */
    void moveLeft(final int distance);

    /**
     * Move to the right.
     * 
     * @param distance The distance to travel.
     */
    void moveRight(final int distance);
}

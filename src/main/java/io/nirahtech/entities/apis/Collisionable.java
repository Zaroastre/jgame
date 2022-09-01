package io.nirahtech.entities.apis;

import io.nirahtech.entities.Entity;

/**
 * Intrface that expose API for a Collisionable object.
 */
public interface Collisionable<T extends Entity> {

    /**
     * Check if object is in collision state.
     * 
     * @return The collision state.
     */
    boolean isCollision();

    /**
     * Set the collision state.
     * 
     * @param collision The new collision state.
     */
    void setCollision(boolean collision);

    /**
     * Check if object is in collision with another object.
     * 
     * @param otherEntity Other object to check.
     * @return {@code true} if collision is deteected, else {@code false}.
     */
    boolean isInCollisionWith(T otherEntity);

    
}

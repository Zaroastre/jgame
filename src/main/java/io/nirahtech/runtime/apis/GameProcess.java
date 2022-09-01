package io.nirahtech.runtime.apis;

import java.awt.Graphics;

/**
 * Interface that exposes API for the Game Process tasks.
 */
public interface GameProcess {

    /**
     * In this first step, event handling and computation are processed.
     */
    void update();

    /**
     * In this second step, graphics are updated while painting screen.
     * 
     * @param graphics The graphic context where draw.
     */
    void paintComponent(final Graphics graphics);
}

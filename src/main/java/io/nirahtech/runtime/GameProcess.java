package io.nirahtech.runtime;

import java.awt.Graphics;

public interface GameProcess {
    void update();

    void paintComponent(final Graphics graphics);
}

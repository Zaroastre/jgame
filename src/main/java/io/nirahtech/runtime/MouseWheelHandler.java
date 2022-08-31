package io.nirahtech.runtime;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MouseWheelHandler implements MouseWheelListener, Initializable {

    private static final Logger LOGGER = Logger.getLogger(MouseWheelHandler.class.getSimpleName());
    private static MouseWheelHandler instance;

    public static final MouseWheelHandler getInstance() {
        LOGGER.info("Calling unique instance of mouse wheel handler");
        if (MouseWheelHandler.instance == null) {
            MouseWheelHandler.instance = new MouseWheelHandler();
        }
        return MouseWheelHandler.instance;
    }

    private Zoomable zoomable;

    private MouseWheelHandler() {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (zoomable != null) {
            if (event.getWheelRotation() == 1) {
                zoomable.zoomOut();
            } else {
                zoomable.zoomIn();
            }
        }

    }

    @Override
    public void initialize(ResourceBundle configuration) {
        LOGGER.info("Initializing mouse wheel handler instance...");
        this.zoomable = GamePanel.getInstance();
        LOGGER.info("Mouse wheel handler instance initialized.");

    }

}

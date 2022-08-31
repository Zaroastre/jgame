package io.nirahtech.runtime;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ResourceBundle;

public class MouseWheelHandler implements MouseWheelListener, Initializable {

    private static MouseWheelHandler instance;

    public static final MouseWheelHandler getInstance() {
        if (MouseWheelHandler.instance == null) {
            MouseWheelHandler.instance = new MouseWheelHandler(null);
        }
        return MouseWheelHandler.instance;
    }

    private Zoomable zoomable;

    private MouseWheelHandler(final Zoomable zoomable) {
        this.zoomable = zoomable;
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
        this.zoomable = GamePanel.getInstance();

    }

}

package io.nirahtech.runtime;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class KeyboardHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed = false;

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    @Override
    public void keyTyped(KeyEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent event) {
        // TODO Auto-generated method stub
        switch (event.getKeyCode()) {
            case KeyEvent.VK_Z:
                this.upPressed = true;
                break;
            case KeyEvent.VK_S:
                this.downPressed = true;
                break;
            case KeyEvent.VK_Q:
                this.leftPressed = true;
                break;
            case KeyEvent.VK_D:
                this.rightPressed = true;
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_Z:
                this.upPressed = false;
                break;
            case KeyEvent.VK_S:
                this.downPressed = false;
                break;
            case KeyEvent.VK_Q:
                this.leftPressed = false;
                break;
            case KeyEvent.VK_D:
                this.rightPressed = false;
                break;
            default:
                break;
        }
    }
}

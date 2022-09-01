package io.nirahtech.menus;

import java.awt.Graphics;

public class MainMenu extends Menu {

    private static enum MainMenuItem {
        NEW_GAME,
        CONTINUE,
        QUIT;
    }

    private MainMenuItem selectedMenuItem = MainMenuItem.NEW_GAME;

    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        // TODO Auto-generated method stub

    }

}

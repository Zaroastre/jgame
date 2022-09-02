package io.nirahtech.menus;

import java.awt.Color;
import java.awt.Graphics;

import io.nirahtech.enumerations.GameStep;

public class MainMenu extends Menu {

    private static Menu instance;

    public static final Menu getInstance() {
        if (MainMenu.instance == null) {
            MainMenu.instance = new MainMenu();
        }
        return MainMenu.instance;
    }

    private MainMenuItem selectedMenuItem = MainMenuItem.NEW_GAME;
    private int userMenuChoice = selectedMenuItem.ordinal();

    @Override
    public void update() {
        if (Menu.keyboardHandler.isDownPressed() && !Menu.keyboardHandler.isEnterPressed()) {
            if (MainMenuItem.getMainMenuItemByOrdinal(userMenuChoice + 1) != null) {
                userMenuChoice++;
                selectedMenuItem = MainMenuItem.getMainMenuItemByOrdinal(userMenuChoice);
            }
        } else if (Menu.keyboardHandler.isUpPressed() && !Menu.keyboardHandler.isEnterPressed()) {
            if (MainMenuItem.getMainMenuItemByOrdinal(userMenuChoice - 1) != null) {
                userMenuChoice--;
                selectedMenuItem = MainMenuItem.getMainMenuItemByOrdinal(userMenuChoice);
            }
        } else if (Menu.keyboardHandler.isEnterPressed()) {
            switch (selectedMenuItem) {
                case NEW_GAME:
                case CONTINUE:
                    Menu.gamePanel.setGameStep(GameStep.IN_GAME);
                    break;
                case QUIT:
                    Menu.gamePanel.setGameStep(GameStep.GAME_OVER);
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Menu.gamePanel.getScreenWidth(), Menu.gamePanel.getScreenHeight());
        graphics.setColor(Color.WHITE);
        int index = 0;
        for (MainMenuItem menuItem : MainMenuItem.values()) {
            String text = menuItem.name().replace("_", " ");
            if (this.selectedMenuItem == menuItem) {
                text = String.format("> %s", text);
            }
            int length = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
            graphics.drawString(text, (Menu.gamePanel.getScreenWidth() / 2) - (length / 2),
                    (Menu.gamePanel.getScreenHeight() / 2) + 50 * index);
            index++;

        }
    }

}

package io.nirahtech.menus;

enum MainMenuItem {
    NEW_GAME,
    CONTINUE,
    QUIT;

    public static final MainMenuItem getMainMenuItemByOrdinal(final int ordinal) {
        MainMenuItem result = null;
        for (MainMenuItem item : MainMenuItem.values()) {
            if (item.ordinal() == ordinal) {
                result = item;
                break;
            }
        }
        return result;
    }

}

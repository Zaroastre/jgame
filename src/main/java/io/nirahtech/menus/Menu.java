package io.nirahtech.menus;

import java.awt.Graphics;
import java.util.ResourceBundle;

import io.nirahtech.runtime.Initializable;
import io.nirahtech.runtime.apis.GameProcess;
import io.nirahtech.runtime.io.handlers.KeyboardHandler;

public abstract class Menu implements GameProcess, Initializable {

    protected static KeyboardHandler keyboardHandler;;

    @Override
    public void initialize(ResourceBundle configuration) {
        keyboardHandler = KeyboardHandler.getInstance();
    }
}

package io.nirahtech.entities.characters.npcs;

import java.awt.Point;

import io.nirahtech.entities.characters.Character;

public class NonPlayableCharacter extends Character {

    protected NonPlayableCharacter(final Point mapLocation, final Point screenLocation) {
        super(mapLocation, screenLocation);
    }

}

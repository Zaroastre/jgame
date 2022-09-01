package io.nirahtech.entities.characters;

import java.awt.Point;

public class NonPlayableCharacter extends Entity {

    protected NonPlayableCharacter(Point mapLocation, Point screenLocation) {
        super(mapLocation, screenLocation);
    }

    @Override
    public Point getPositionOnTheScreen() {
        // TODO Auto-generated method stub
        return null;
    }

}

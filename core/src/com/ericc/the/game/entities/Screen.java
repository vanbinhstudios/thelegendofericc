package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.components.ScreenBoundariesComponent;

public class Screen extends Entity {

    public Screen() {
        add(new ScreenBoundariesComponent());
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.components.ScreenBoundariesComponent;
import com.ericc.the.game.map.CurrentMap;

public class Screen extends Entity {

    public Screen() {
        ScreenBoundariesComponent bounds = new ScreenBoundariesComponent();
        bounds.bottom = 0;
        bounds.top = CurrentMap.map.height();
        bounds.left = 0;
        bounds.right = CurrentMap.map.width();

        add(bounds);
    }
}

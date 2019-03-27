package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.map.Map;

// The logical position of the entity (on the map).
public class PositionComponent implements Component {
    public int x;
    public int y;
    public Map map;

    public PositionComponent(int x, int y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.Direction;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

// The logical position of the entity (on the map).
public class PositionComponent implements Component {
    public GridPoint xy;
    public Direction dir;
    public Map map;

    public PositionComponent(GridPoint xy, Direction dir, Map map) {
        this.xy = xy;
        this.dir = dir;
        this.map = map;
    }

    public PositionComponent(GridPoint xy, Map map) {
        this(xy, Direction.DOWN, map);
    }

    public PositionComponent(PositionComponent pos) {
        this(pos.xy, pos.dir, pos.map);
    }

    public int getX() {
        return xy.x;
    }

    public int getY() {
        return xy.y;
    }
}

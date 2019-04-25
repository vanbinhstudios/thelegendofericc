package com.ericc.the.game.utils;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.PositionComponent;

public abstract class Area {
    private PositionComponent source;

    public Area(PositionComponent source) {
        this.source = new PositionComponent(source);
    }

    public abstract GridPoint topRight();

    public abstract GridPoint bottomLeft();

    protected abstract boolean containsLocal(int x, int y);

    public boolean contains(int x, int y) {
        Direction dir = source.direction;
        x -= source.xy.x;
        y -= source.xy.y;
        if (dir == Direction.DOWN) {
            x = -x;
            y = -y;
        } else if (dir == Direction.LEFT) {
            int tmp = y;
            y = -x;
            x = tmp;
        } else if (dir == Direction.RIGHT) {
            int tmp = y;
            y = x;
            x = -tmp;
        }
        return containsLocal(x, y);
    }
}

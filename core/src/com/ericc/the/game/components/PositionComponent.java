package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.Direction;

public class PositionComponent implements Component {

    public int x;
    public int y;
    public Direction dir;

    public PositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
        this.dir = Direction.S;
    }
}

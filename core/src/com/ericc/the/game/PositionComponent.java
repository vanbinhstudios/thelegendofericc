package com.ericc.the.game;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {

    public int x;
    public int y;
    Direction dir;

    public PositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
        this.dir = Direction.S;
    }
}

package com.ericc.the.game.utils;

import com.ericc.the.game.components.PositionComponent;

public class RayArea extends Area {
    int radius;
    private GridPoint topRight;
    private GridPoint bottomLeft;

    public RayArea(PositionComponent source, int radius) {
        super(source);
        this.radius = radius;
        this.bottomLeft = source.xy.shift(0, 1);
        this.topRight = source.xy.shift(0, radius);
    }

    @Override
    public GridPoint bottomLeft() {
        return bottomLeft;
    }

    @Override
    public GridPoint topRight() {
        return topRight;
    }

    @Override
    protected boolean containsLocal(int x, int y) {
        return x == 0 && 0 < y && y <= radius;
    }
}

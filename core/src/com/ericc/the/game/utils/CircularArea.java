package com.ericc.the.game.utils;

import com.ericc.the.game.components.PositionComponent;

public class CircularArea extends Area {
    int radius;
    private GridPoint topRight;
    private GridPoint bottomLeft;

    public CircularArea(PositionComponent source, int radius) {
        super(source);
        this.radius = radius;
        this.bottomLeft = source.xy.shift(-radius, -radius);
        this.topRight = source.xy.shift(radius, radius);
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
        return x * x + y * y < radius * radius;
    }
}

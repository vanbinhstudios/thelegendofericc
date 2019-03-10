package com.ericc.the.game;

public enum TileTextureIndicator {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3),
    FLOOR(4);

    private final int direction;

    TileTextureIndicator(int direction) {
        this.direction = direction;
    }

    public int getValue() {
        return direction;
    }

    public static int countValues() {
        return 5;
    }
}

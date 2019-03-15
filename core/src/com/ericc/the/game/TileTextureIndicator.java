package com.ericc.the.game;

/**
 * Indicates the indexes of every possible tile's texture dimension.
 */
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

    /**
     * @return the number of possible values of this enum
     */
    public static int countValues() {
        return 5;
    }
}

package com.ericc.the.game;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    /**
     * @return the number of possible values of this enum
     */
    public static int countValues() {
        return values().length;
    }

    public int getValue() {
        return direction;
    }
}

package com.ericc.the.game;

import com.ericc.the.game.utils.GridPoint;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3),
    NONE(5);

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

    public static Direction fromGridPoint(GridPoint gp) {
        if (gp.x > 0) {
            return RIGHT;
        }

        if (gp.x < 0) {
            return LEFT;
        }

        if (gp.y > 0) {
            return UP;
        }

        if (gp.y < 0) {
            return DOWN;
        }

        return NONE;
    }

    public static Direction fromGridPoints(GridPoint source, GridPoint goal) {
        return fromGridPoint(source.subtract(goal));
    }

    public int getValue() {
        return direction;
    }
}

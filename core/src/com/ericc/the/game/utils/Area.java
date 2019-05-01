package com.ericc.the.game.utils;

import com.ericc.the.game.Direction;

public class Area {
    public int left, bottom, right, top;

    public Area(int left, int bottom, int right, int top) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public static Area square(GridPoint center, int radius) {
        return new Area(
                center.x - radius, center.y - radius,
                center.x + radius, center.y + radius
        );
    }

    public static Area ray(GridPoint start, Direction dir, int length) {
        switch (dir) {
            case UP:
                return new Area(
                        start.x, start.y + 1,
                        start.x, start.y + length);
            case DOWN:
                return new Area(
                        start.x, start.y - length,
                        start.x, start.y - 1);
            case RIGHT:
                return new Area(
                        start.x + 1, start.y,
                        start.x + length, start.y);
            case LEFT:
                return new Area(
                        start.x - length, start.y,
                        start.x - 1, start.y);

        }
        return null;
    }
}

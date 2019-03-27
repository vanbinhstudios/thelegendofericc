package com.ericc.the.game.map;

import com.badlogic.gdx.math.GridPoint2;

public class Room {

    private GridPoint2 leftDownCorner, rightUpperCorner;
    private int id;

    private static int counter = 0;

    Room(int x1, int y1, int x2, int y2) {
        this(new GridPoint2(x1, y1), new GridPoint2(x2, y2));
    }

    Room(GridPoint2 leftDownCorner, GridPoint2 rightUpperCorner) {
        this.leftDownCorner = leftDownCorner;
        this.rightUpperCorner = rightUpperCorner;

        ++counter;
        id = counter;
    }

    public GridPoint2 getCentre() {
        return new GridPoint2((leftDownCorner.x + rightUpperCorner.x) / 2,
                (leftDownCorner.y + rightUpperCorner.y) / 2);
    }

    public GridPoint2 getLeftDownCorner() {
        return leftDownCorner;
    }

    public GridPoint2 getRightUpperCorner() {
        return rightUpperCorner;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Room) {
            return ((Room) o).id == id;
        }

        return false;
    }

    /**
     * @return an integer - the minimal dimension of this room
     */
    public int getMinDimension() {
        return Math.min(Math.abs(rightUpperCorner.x - leftDownCorner.x),
                Math.abs(rightUpperCorner.y - leftDownCorner.y));
    }
}

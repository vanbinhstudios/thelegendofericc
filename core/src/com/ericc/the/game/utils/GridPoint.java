package com.ericc.the.game.utils;

public class GridPoint {
    public final int x;
    public final int y;

    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GridPoint shift(int x, int y) {
        return new GridPoint(this.x + x, this.y + y);
    }

    public GridPoint add(GridPoint other) {
        return shift(other.x, other.y);
    }

    public GridPoint subtract(GridPoint other) {
        return shift(-other.x, -other.y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        GridPoint g = (GridPoint) o;
        return this.x == g.x && this.y == g.y;
    }

    @Override
    public int hashCode() {
        final int prime = 53;
        int result = 1;
        result = prime * result + this.x;
        result = prime * result + this.y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

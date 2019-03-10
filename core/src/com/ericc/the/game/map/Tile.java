package com.ericc.the.game.map;

import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Entity;

public class Tile extends Entity {
    private boolean passable; ///< Indicates whether a single tile should be accessible by a player

    Tile(int x, int y, int size, boolean passable) {
        super(new GridPoint2(x * size, y * size), size, size);
        this.passable = passable;
    }

    @Override
    public String toString() {
        return passable ? "1" : "0";
    }

    static Tile generateCorridor(int x, int y, int size) {
        return new Tile(x, y, size, true);
    }

    static Tile generateImpassableTile(int x, int y, int size) {
        return new Tile(x, y, size, false);
    }

    static Tile generateRoomTile(int x, int y, int size) {
        return new Tile(x, y, size, true);
    }

    boolean isPassable() {
        return passable;
    }
}


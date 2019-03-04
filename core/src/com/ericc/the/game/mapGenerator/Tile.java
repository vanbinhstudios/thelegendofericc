package com.ericc.the.game.mapGenerator;

public class Tile {
    private boolean passable;

    Tile(boolean active) {
        this.passable = active;
    }

    @Override
    public String toString() {
        return passable ? "1" : "0";
    }

    static Tile generateCorridor() {
        return new Tile(true);
    }

    static Tile generateNotAvailableTile() {
        return new Tile(false);
    }

    static Tile generateRoomTile() {
        return new Tile(true);
    }

    boolean isPassable() {
        return passable;
    }
}

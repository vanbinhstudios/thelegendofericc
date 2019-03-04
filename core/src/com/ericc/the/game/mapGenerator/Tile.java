package com.ericc.the.game.mapGenerator;

public class Tile {
    private boolean active;

    Tile(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return active ? "1" : "0";
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

    boolean isAvailable() {
        return active;
    }
}

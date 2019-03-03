package com.ericc.the.game;

public class Tile {
    private boolean active;

    public Tile(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return active ? "1" : "0";
    }
}

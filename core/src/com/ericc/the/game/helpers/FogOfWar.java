package com.ericc.the.game.helpers;

public class FogOfWar {
    private boolean[][] tileSeen; ///< any tile that was seen by the hero from the start

    public FogOfWar(int width, int height) {
        tileSeen = new boolean[width][height];
    }

    /**
     * Registers a tile in a fog of war structure, marks it as seen.
     */
    public void registerTile(int x, int y) {
        tileSeen[x][y] = true;
    }

    /**
     * Returns whether an object at given position has even been in any fov.
     */
    public boolean hasBeenRegistered(int x, int y) {
        return tileSeen[x][y];
    }
}

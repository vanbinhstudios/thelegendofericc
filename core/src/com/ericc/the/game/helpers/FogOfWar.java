package com.ericc.the.game.helpers;

import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.RectangularBitset;

public class FogOfWar {
    private RectangularBitset tileSeen; ///< any tile that was seen by the hero from the start

    public FogOfWar(int width, int height) {
        tileSeen = new RectangularBitset(width, height);
    }

    /**
     * Registers a tile in a fog of war structure, marks it as seen.
     */
    public void markAsSeenByPlayer(GridPoint xy) {
        tileSeen.set(xy);
    }

    /**
     * Returns whether an object at given position has even been in any fov.
     */
    public boolean hasBeenSeenByPlayer(int x, int y) {
        return tileSeen.get(x, y);
    }
}

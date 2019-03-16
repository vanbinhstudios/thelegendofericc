package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.ericc.the.game.Media;

/**
 * A system which (after every interval) changes
 * every tile at position [i, j] which satisfies the condition below:
 * (i + j) % (#intervals_pased % 2) == 0,
 * to the 'reversed tile'. Consequently providing a feel of
 * a dancing gameplay.
 */
public class TileChanger extends IntervalSystem {
    private int ctr; ///< counter - counts the number of updateInterval calls

    public TileChanger(float interval) {
        super(interval);
        ctr = 0;
    }

    @Override
    public void addedToEngine(Engine engine) {
    }

    @Override
    protected void updateInterval() {
        ++ctr;

        Media.floorsConfiguration = ctr % 2;
    }
}
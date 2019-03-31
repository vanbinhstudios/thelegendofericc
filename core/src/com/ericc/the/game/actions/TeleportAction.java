package com.ericc.the.game.actions;

import com.ericc.the.game.map.StaircaseDestination;

public class TeleportAction extends Action {
    public StaircaseDestination dest;

    public TeleportAction(StaircaseDestination dest) {
        this.dest = dest;
    }

    // TODO How to solve the mysterious case of stairs?
    @Override
    public int getBaseTimeCost() {
        return 100;
    }
}

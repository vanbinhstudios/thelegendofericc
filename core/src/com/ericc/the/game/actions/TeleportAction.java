package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Entity;

public class TeleportAction extends Action {
    public Entity stairs;

    public TeleportAction(Entity stairs) {
        this.stairs = stairs;
    }
}

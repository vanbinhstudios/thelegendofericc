package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TeleportScheduledComponent implements Component {
    public Entity stairs;

    public TeleportScheduledComponent(Entity stairs) {
        this.stairs = stairs;
    }
}

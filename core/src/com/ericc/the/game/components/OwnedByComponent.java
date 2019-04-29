package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class OwnedByComponent implements Component {
    public Entity owner;

    public OwnedByComponent(Entity owner) {
        this.owner = owner;
    }
}

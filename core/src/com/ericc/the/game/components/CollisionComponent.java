package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {
    public boolean isPushable;

    public CollisionComponent() {
        this.isPushable = false;
    }

    public CollisionComponent(boolean isPushable) {
        this.isPushable = isPushable;
    }
}

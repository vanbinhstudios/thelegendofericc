package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {
    public enum Type {CRATE, TRAP, LIVING}

    public Type type;

    public CollisionComponent(Type type) {
        this.type = type;
    }
}

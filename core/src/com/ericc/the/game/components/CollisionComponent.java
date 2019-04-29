package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {
    public Type type;

    public CollisionComponent(Type type) {
        this.type = type;
    }

    public enum Type {CRATE, TRAP, LIVING}
}

package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.PositionComponent;

public class RotateTo implements Effect {
    private Direction dir;

    public RotateTo(Direction dir) {
        this.dir = dir;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        if (pos != null) {
            pos.dir = dir;
        }
    }
}

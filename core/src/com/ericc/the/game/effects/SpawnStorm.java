package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Storm;

public class SpawnStorm implements Effect {
    @Override
    public void apply(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        if (pos != null) {
            engine.addEntity(new Storm(pos.xy, pos.map, entity));
        }
    }
}

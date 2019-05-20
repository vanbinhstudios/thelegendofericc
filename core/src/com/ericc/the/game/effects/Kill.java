package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;
import com.ericc.the.game.entities.Loot;

public class Kill implements Effect {
    private Entity killer;

    public Kill(Entity killer) {
        this.killer = killer;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        entity.remove(AgencyComponent.class);
        entity.remove(CollisionComponent.class);
        entity.add(new DeadTag());
        new SetAnimation(AnimationState.DYING).apply(entity, engine);

        if (Mappers.drop.has(entity) && Mappers.position.has(entity)) {
            PositionComponent pos = Mappers.position.get(entity);
            DropComponent drop = Mappers.drop.get(entity);
            Item itemComponent = drop.drop.drop();
            if (itemComponent != null) {
                Loot item = new Loot(pos.xy, pos.map, itemComponent);
                engine.addEntity(item);
                pos.map.lootMap.put(pos.xy, item);
            }
        }

        if (killer != null && Mappers.experienceWorth.has(entity)) {
            new IncreaseExperience(Mappers.experienceWorth.get(entity).experienceWorth).apply(killer, engine);
        }
    }
}

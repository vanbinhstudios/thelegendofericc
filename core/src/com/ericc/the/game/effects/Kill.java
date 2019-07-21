package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;
import com.ericc.the.game.entities.Loot;
import com.ericc.the.game.items.Item;

public class Kill implements Effect {
    private Entity killer;

    public Kill(Entity killer) {
        this.killer = killer;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        StatsComponent stats = Mappers.stats.get(entity);

        if (stats != null) {
            stats.lives--;

            if (stats.lives > 0) {
                int rand = MathUtils.random(0, 2);

                if (rand == 0) {
                    stats.health = stats.maxHealth;
                    new SetAnimation(AnimationState.REBIRTH).apply(entity, engine);

                    return;
                }
            }
        }

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

package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DeadTag;
import com.ericc.the.game.components.RenderableComponent;

public class GarbageCollector extends IteratingSystem {
    public GarbageCollector(int priority) {
        super(Family.all(DeadTag.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RenderableComponent renderable = Mappers.renderable.get(entity);
        DeadTag tag = Mappers.dead.get(entity);
        if (renderable == null || !renderable.visible || tag.time > 5.0f) {
            getEngine().removeEntity(entity);
        } else {
            tag.time += deltaTime;
        }
    }
}

package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;

public class DirectedSpritesheetSystem extends EntitySystem {
    private ImmutableArray<Entity> directed; // Entities with appearance varying with their orientation.

    public DirectedSpritesheetSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        directed = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        // Update sprites according to orientation (face direction) of the Entity.
        for (Entity entity : directed) {
            RenderableComponent render = Mappers.renderable.get(entity);
            PositionComponent pos = Mappers.position.get(entity);

            render.region = render.model.sheet[pos.direction.getValue()];
        }
    }
}

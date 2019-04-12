package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.AttackComponent;
import com.ericc.the.game.components.CollisionComponent;
import com.ericc.the.game.components.DeathComponent;

public class DeathSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public DeathSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(DeathComponent.class).get());
    }

    // TODO Entities should be properly deleted from the engine (some kind of periodic GC?)
    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            entity.remove(AgencyComponent.class);
            entity.remove(CollisionComponent.class);
            entity.remove(AttackComponent.class);
        }
    }
}

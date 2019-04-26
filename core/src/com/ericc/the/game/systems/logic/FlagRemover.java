package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.components.DirtyFlag;


public class FlagRemover extends EntitySystem {
    private ImmutableArray<Entity> entities; ///< all entities able to discover new tiles

    public FlagRemover(int priority) {
        super(priority); // Depends on FieldOfViewSystem
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(DirtyFlag.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            entity.remove(DirtyFlag.class);
        }
    }
}

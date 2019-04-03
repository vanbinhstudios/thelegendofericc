package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DeathComponent;
import com.ericc.the.game.components.RenderableComponent;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class DeathSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private Engine engine;

    public DeathSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, DeathComponent.class).get());
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            RenderableComponent render = Mappers.renderable.get(entity);
            DeathComponent death = Mappers.death.get(entity);


        }
    }

    p
}

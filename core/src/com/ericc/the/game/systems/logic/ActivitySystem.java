package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.ericc.the.game.GameEngine;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.ActiveComponent;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class ActivitySystem extends EntitySystem {
    private ImmutableArray<Entity> sapient;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> active;

    private Array<Entity> pending = new Array<>(false, 512);
    private GameEngine gameEngine;

    public ActivitySystem(GameEngine gameEngine, int priority) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void addedToEngine(Engine engine) {
        sapient = engine.getEntitiesFor(Family.all(AgencyComponent.class, StatsComponent.class).get());
        entities = engine.getEntitiesFor(Family.all(AgencyComponent.class).get());
        active = engine.getEntitiesFor(Family.all(ActiveComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : active) {
            entity.remove(ActiveComponent.class);
        }

        if (pending.isEmpty()) {
            for (Entity entity : sapient) {
                StatsComponent stats = Mappers.stats.get(entity);
                Mappers.agency.get(entity).initiative = ((stats.agility + stats.intelligence) / 4
                        + ThreadLocalRandom.current().nextInt(1, 20));
            }

            for (Entity entity : entities) {
                pending.add(entity);
            }

            pending.sort(Comparator.comparingInt(a -> Mappers.agency.get(a).initiative));
        }

        Entity entity = pending.pop();
        entity.add(ActiveComponent.ACTIVE);

        if (pending.isEmpty()) {
            gameEngine.stopSpinning();
        }
    }
}

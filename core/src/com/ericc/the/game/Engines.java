package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.ericc.the.game.components.MobComponent;

public class Engines {
    private Engine logicEngine;
    private Engine realtimeEngine;

    public Engines() {
        this.logicEngine = new Engine();
        this.realtimeEngine = new Engine();
    }

    public Engines(Engine logicEngine, Engine realtimeEngine) {
        this.logicEngine = logicEngine;
        this.realtimeEngine = realtimeEngine;
    }

    public void addEntityToBothEngines(Entity entity) {
        addEntityToLogicEngine(entity);
        addEntityToRealtimeEngine(entity);
    }

    public void removeEntitiesFromLogicEngine(Family family) {
        removeEntitiesFromEngine(this.logicEngine, family);
    }


    public void removeEntitiesFromRealtimeEngine(Family family) {
        removeEntitiesFromEngine(this.realtimeEngine, family);
    }

    public void removeEntitiesFromBothEngines(Family family) {
        removeEntitiesFromLogicEngine(family);
        removeEntitiesFromRealtimeEngine(family);
    }

    private void removeEntitiesFromEngine(Engine engine, Family family) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        System.out.println(entities.size());

        for (Entity entity : entities) {
            engine.removeEntity(entity);
        }
    }

    public void addEntityToLogicEngine(Entity entity) {
        logicEngine.addEntity(entity);
    }

    public void addEntityToRealtimeEngine(Entity entity) {
        realtimeEngine.addEntity(entity);
    }

    public void updateLogicEngine() {
        logicEngine.update(1);
    }

    public void updateRealtimeEngine() {
        realtimeEngine.update(Gdx.graphics.getDeltaTime());
    }

    public Engine getLogicEngine() {
        return logicEngine;
    }

    public Engine getRealtimeEngine() {
        return realtimeEngine;
    }
}

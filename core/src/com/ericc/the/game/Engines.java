package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

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

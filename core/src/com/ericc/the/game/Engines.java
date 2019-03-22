package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Engines {
    private Engine engine = new Engine();
    private ArrayList<EntitySystem> logicSystems = new ArrayList<>();
    private ArrayList<EntitySystem> realtimeSystems = new ArrayList<>();

    public void updateLogicEngine() {
        for (EntitySystem es : realtimeSystems)
            es.setProcessing(false);
        for (EntitySystem es : logicSystems)
            es.setProcessing(true);

        engine.update(1);

        for (EntitySystem es : realtimeSystems)
            es.setProcessing(true);
        for (EntitySystem es : logicSystems)
            es.setProcessing(false);
    }

    public void updateRealtimeEngine() {
        engine.update(Gdx.graphics.getDeltaTime());
    }

    public void addLogicSystem(EntitySystem system) {
        logicSystems.add(system);
        system.setProcessing(false);
        engine.addSystem(system);
    }

    public void addRealtimeSystem(EntitySystem system) {
        realtimeSystems.add(system);
        engine.addSystem(system);
    }

    public void addEntity(Entity entity) {
        engine.addEntity(entity);
    }
}
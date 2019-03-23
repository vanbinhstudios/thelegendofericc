package com.ericc.the.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.ericc.the.game.systems.logic.FieldOfViewSystem;
import com.ericc.the.game.systems.logic.FogOfWarSystem;

import java.util.ArrayList;

public class Engines {
    private Engine engine = new Engine();
    private ArrayList<EntitySystem> logicSystems = new ArrayList<>();
    private ArrayList<EntitySystem> realtimeSystems = new ArrayList<>();

    private EntitySystem fov;
    private EntitySystem fog;

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

        if (system instanceof FieldOfViewSystem) {
            this.fov = system;
        } else if (system instanceof FogOfWarSystem) {
            this.fog = system;
        }
    }

    public void addRealtimeSystem(EntitySystem system) {
        realtimeSystems.add(system);
        engine.addSystem(system);
    }

    public void addEntity(Entity entity) {
        engine.addEntity(entity);
    }

    public void removeFamily(Family family) {
        System.out.println(getEntitiesFor(family).size());

        engine.removeAllEntities(family);

        System.out.println(getEntitiesFor(family).size());
    }

    public void updatePlayersVision() {
        this.fov.update(0);
        this.fog.update(0);
    }

    public void removeEntity(Entity entity) {
        engine.removeEntity(entity);
    }

    public ImmutableArray<Entity> getEntitiesFor(Family family) {
        return engine.getEntitiesFor(family);
    }
}

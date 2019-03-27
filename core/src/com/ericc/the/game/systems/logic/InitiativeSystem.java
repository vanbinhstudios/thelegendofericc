package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.InitiativeComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.concurrent.ThreadLocalRandom;


public class InitiativeSystem extends IteratingSystem {

    public InitiativeSystem() {
        super(Family.all(InitiativeComponent.class, StatsComponent.class).get(),
                0); // Depends on nothing.
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        // Rolling for value for every entity that is capable of independent decision-making
        StatsComponent stats = Mappers.stats.get(entity);
        InitiativeComponent initiative = Mappers.initiative.get(entity);
        initiative.value = (stats.agility + stats.intelligence) / 4
                + ThreadLocalRandom.current().nextInt(1, 20);
    }
}

package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;

import java.util.concurrent.ThreadLocalRandom;


public class InitiativeSystem extends IteratingSystem {

    public InitiativeSystem() {
        super(Family.all(PositionComponent.class,
                CurrentActionComponent.class, AgilityComponent.class,
                IntelligenceComponent.class, SentienceComponent.class,
                InitiativeComponent.class).get(),
                10001);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        // Rolling for initiative for every entity that is capable of independent decision-making
        AgilityComponent entityAgility = Mappers.agility.get(entity);
        IntelligenceComponent entityIntelligence = Mappers.intelligence.get(entity);
        Integer initiative = (entityAgility.value + entityIntelligence.value) / 4
                                + ThreadLocalRandom.current().nextInt(1, 20);
        Mappers.initiative.get(entity).value = initiative;
    }
}

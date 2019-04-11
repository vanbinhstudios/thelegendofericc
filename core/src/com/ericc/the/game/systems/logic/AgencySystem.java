package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.ActiveComponent;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

public class AgencySystem extends IteratingSystem {
    public AgencySystem(int priority) {
        super(Family.all(ActiveComponent.class, AgencyComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AgencyComponent agency = Mappers.agency.get(entity);
        PositionComponent pos = Mappers.position.get(entity);
        StatsComponent stats = Mappers.stats.get(entity);

        Action action = agency.agency.chooseAction(pos, stats);
        agency.delay += action.getDelay() * ((stats != null) ? stats.delayMultiplier : 1.0);
        entity.add(action);
    }
}

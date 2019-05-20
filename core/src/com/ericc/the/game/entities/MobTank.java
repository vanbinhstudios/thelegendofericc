package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.ChargeToPlayerAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class MobTank extends Entity {
    public MobTank(GridPoint xy, Map map) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.tank)); // TODO add new model
        add(new StatsComponent(45, 30, 5, 150, 0));
        add(new AgencyComponent(new ChargeToPlayerAgency(), false));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(new HostileTag());
        add(new ExperienceWorthComponent(500));
        add(new HealthBarComponent(Models.healthBar));
        add(new AnimationComponent());
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.BossAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Boss extends Entity {
    public Boss(GridPoint xy, Map map) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.mage)); // TODO add new model
        add(new StatsComponent(45, 30, 5, 1000, 200, true));
        add(new AgencyComponent(new BossAgency(), false));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(new HostileTag());
        add(new ExperienceWorthComponent(5000));
        add(new HealthBarComponent(Models.healthBar));
        add(new AnimationComponent());
        add(new BossTag());
    }
}

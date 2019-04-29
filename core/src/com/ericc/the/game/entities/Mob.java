package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.RandomWalk;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Mob extends Entity {
    public Mob(GridPoint xy, Map map) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.mage));
        add(new StatsComponent(45, 30, 20, 120));
        add(new AgencyComponent(new RandomWalk(), false));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(new HostileTag());
        add(new ExperienceWorthComponent(300));
        add(new HealthBarComponent(Models.healthBar));
        add(new AnimationComponent());
    }
}

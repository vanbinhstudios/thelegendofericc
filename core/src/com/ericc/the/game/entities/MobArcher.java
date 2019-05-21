package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.ArcherAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class MobArcher extends Entity {
    public MobArcher(GridPoint xy, Map map) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.slimeBig)); // TODO change model
        add(new StatsComponent(45, 50, 10, 70, false));
        add(new AgencyComponent(new ArcherAgency(), false));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(new HostileTag());
        add(new ExperienceWorthComponent(600));
        add(new HealthBarComponent(Models.healthBar));
        add(new AnimationComponent());
    }
}

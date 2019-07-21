package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.ChargeToPlayerAgency;
import com.ericc.the.game.agencies.CowAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.drops.CowDrop;
import com.ericc.the.game.drops.ExampleDrop;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Cow extends Entity {
    public Cow(GridPoint xy, Map map) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.tank)); // TODO add new model
        add(new StatsComponent(10, 10, 50, 200, 0));
        add(new AgencyComponent(new CowAgency(), false));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(new ExperienceWorthComponent(100));
        add(new HealthBarComponent(Models.healthBar));
        add(new AnimationComponent());
        add(new DropComponent(new CowDrop()));
    }
}
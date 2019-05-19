package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.StormAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Storm extends Entity {
    public Storm(GridPoint xy, Map map, Entity owner) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(Models.explosion1));
        add(new AgencyComponent(new StormAgency(), false));
        add(new AnimationComponent());
        add(new OwnedByComponent(owner));
        getComponent(RenderableComponent.class).brightness = 1.0f;
        getComponent(RenderableComponent.class).alpha = 1.0f;
        getComponent(AgencyComponent.class).initiative = 10000;
    }
}

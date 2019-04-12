package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.agencies.ProjectileAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Projectile extends Entity {
    public Projectile (GridPoint xy, Map map, Direction dir, int power, Model model) {
        add(new PositionComponent(xy, dir, map));
        add(new AttackComponent(power));
        add(new RenderableComponent(model));
        add(new AgencyComponent(new ProjectileAgency()));
        add(new FixedInitiativeComponent(1000));
        getComponent(RenderableComponent.class).brightness = 1.0f;
        getComponent(RenderableComponent.class).alpha = 1.0f;
    }
}

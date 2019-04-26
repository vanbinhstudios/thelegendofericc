package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.agencies.ProjectileAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Projectile extends Entity {
    public Projectile(GridPoint xy, Map map, Direction dir, int damage, Model model) {
        add(new PositionComponent(xy, dir, map));
        add(new DamageComponent(damage));
        add(new RenderableComponent(model));
        add(new AgencyComponent(new ProjectileAgency(), false));
        add(new AnimationComponent());
        getComponent(RenderableComponent.class).brightness = 1.0f;
        getComponent(RenderableComponent.class).alpha = 1.0f;
        getComponent(AgencyComponent.class).initiative = 10000;
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.AttackComponent;
import com.ericc.the.game.components.DamageComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Attack extends Entity {
    public Attack(GridPoint xy, Map map, Direction direction, int damage) {
        add(new PositionComponent(xy, direction, map));
        add(new AttackComponent());
        add(new DamageComponent(damage));
        add(new RenderableComponent(Models.sword));
        getComponent(RenderableComponent.class).brightness = 1.0f;
        getComponent(RenderableComponent.class).alpha = 1.0f;
    }
}

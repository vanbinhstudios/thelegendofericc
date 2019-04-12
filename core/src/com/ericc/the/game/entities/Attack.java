package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.AttackComponent;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Attack extends Entity {
    public Attack(GridPoint xy, Map map, int damage, Model attackModel) {
        this(attackModel, damage);
        add(new PositionComponent(xy, map));

    }

    public Attack(GridPoint xy, Map map, Direction direction, int damage) {
        this(xy, map, damage, Models.sword, direction);
    }

    public Attack(GridPoint xy, Map map, int damage, Model attackModel, Direction direction) {
        this(attackModel, damage);
        add(new PositionComponent(xy, direction, map));
    }

    private Attack(Model attackModel, int damage) {
        add(new AttackComponent(damage));
        add(new RenderableComponent(attackModel));
        getComponent(RenderableComponent.class).brightness = 1.0f;
        getComponent(RenderableComponent.class).alpha = 1.0f;
    }


}

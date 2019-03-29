package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.AttackComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.map.Map;

public class Attack extends Entity {
    public Attack(int x, int y, Map map, Direction direction) {
        add(new PositionComponent(x, y, map));
        getComponent(PositionComponent.class).direction = direction;
        add(new AttackComponent());
        add(new RenderableComponent(Models.sword));
        getComponent(RenderableComponent.class).brightness = 1.0f;
    }
}

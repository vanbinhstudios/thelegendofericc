package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.CollisionComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class PushableObject extends Entity {
    public PushableObject(GridPoint xy, Map map) {
        RenderableComponent renderable = new RenderableComponent(Models.crate, true);
        add(new PositionComponent(xy, map));
        add(new CollisionComponent(true));
        add(renderable);
    }
}

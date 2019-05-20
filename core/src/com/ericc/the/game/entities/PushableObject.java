package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.*;
import com.ericc.the.game.drops.ExampleDrop;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class PushableObject extends Entity {
    public PushableObject(GridPoint xy, Map map) {
        RenderableComponent renderable = new RenderableComponent(Models.crate, true);
        add(new PositionComponent(xy, map));
        add(new StatsComponent(10, 0, 0, 20, false));
        add(new CollisionComponent(CollisionComponent.Type.CRATE));
        add(new AnimationComponent());
        add(renderable);
        add(new DropComponent(new ExampleDrop()));
    }
}

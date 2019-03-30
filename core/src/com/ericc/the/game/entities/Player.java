package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

/**
 * A mishmash for prototyping. This class should be broken down as soon as proper Actor systems are introduced.
 */
public class Player extends Entity {
    public PositionComponent pos;
    private RenderableComponent renderable;

    public Player(GridPoint xy, Map map, FieldOfViewComponent fov, CameraComponent camera, AgencyComponent agency) {
        pos = new PositionComponent(xy, map);
        renderable = new RenderableComponent(Models.hero);
        add(pos);
        add(renderable);
        add(fov);
        add(new PlayerComponent());
        add(new StatsComponent(50, 100, 50));
        add(new CollisionComponent());
        add(agency);
        add(camera);
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

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
        add(DirtyFlag.DIRTY);
        add(new PlayerTag());
        add(new StatsComponent(50, 100, 50, 200));
        add(new CollisionComponent(CollisionComponent.Type.LIVING));
        add(agency);
        add(camera);
//        add(new HealthBarComponent(Models.healthBar));
//        add(new ExperienceBarComponent(Models.experienceBar));
        add(new AnimationComponent());
        add(new SafetyMapComponent());
        add(new InventoryComponent());
    }
}

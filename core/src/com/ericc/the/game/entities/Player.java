package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.NoAction;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

/**
 * A mishmash for prototyping. This class should be broken down as soon as proper Actor systems are introduced.
 */
public class Player extends Entity {
    public PositionComponent pos;
    private RenderableComponent renderable;
    public CurrentActionComponent currentAction;
    public IntentionComponent intention;

    public Player(int x, int y, Map map, FieldOfViewComponent fov, CameraComponent camera) {
        pos = new PositionComponent(x, y, map);
        renderable = new RenderableComponent(Models.hero);
        currentAction = new CurrentActionComponent(new NoAction());
        intention = new IntentionComponent(new NoAction());
        add(pos);
        add(renderable);
        add(currentAction);
        add(intention);
        add(fov);
        add(new PlayerComponent());
        add(new StatsComponent(50, 100, 50));
        add(new InitiativeComponent());
        add(camera);
    }

    public Player(GridPoint2 pos, Map map, FieldOfViewComponent fov, CameraComponent camera) {
        this(pos.x, pos.y, map, fov, camera);
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Media;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.NoAction;
import com.ericc.the.game.components.*;

/**
 * A mishmash for prototyping. This class should be broken down as soon as proper Actor systems are introduced.
 */
public class Player extends Entity {
    public PositionComponent pos;
    private DirectionComponent dir;
    private RenderableComponent renderable;
    public CurrentActionComponent currentAction;
    public IntentionComponent intention;

    public Player(int x, int y, FieldOfViewComponent fov) {
        pos = new PositionComponent(x, y);
        dir = new DirectionComponent(Direction.DOWN);
        renderable = new RenderableComponent(Models.hero);
        currentAction = new CurrentActionComponent(new NoAction());
        intention = new IntentionComponent(new NoAction());
        add(pos);
        add(renderable);
        add(dir);
        add(currentAction);
        add(intention);
        add(fov);
        add(new PlayerComponent());
        add(new InteractivityComponent());
        add(new SentienceComponent());
        add(new AgilityComponent(50));
        add(new HealthComponent(100));
        add(new IntelligenceComponent(50));
        add(new MovementPointsComponent(100));
        add(new StrengthComponent(40));
        add(new InitiativeComponent(10));
    }

    public Player(GridPoint2 pos, FieldOfViewComponent fov) {
        this(pos.x, pos.y, fov);
    }
}

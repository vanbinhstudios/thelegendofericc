package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Media;
import com.ericc.the.game.actions.NoAction;
import com.ericc.the.game.components.*;

/**
 * A mishmash for prototyping. This class should be broken down as soon as proper Actor systems are introduced.
 */
public class Player extends Entity {
    public PositionComponent pos;
    private DirectionComponent dir;
    private SpriteSheetComponent renderable;
    public CurrentActionComponent currentAction;

    public Player(int x, int y, int width, int height) {
        pos = new PositionComponent(x, y);
        dir = new DirectionComponent(Direction.DOWN);
        renderable = new SpriteSheetComponent(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft);
        renderable.sprite.setOrigin(0, -0.35f);
        currentAction = new CurrentActionComponent(new NoAction());
        add(pos);
        add(renderable);
        add(dir);
        add(currentAction);
        add(new FieldOfViewComponent(width, height));
        add(new PlayerComponent());
    }

    public Player(GridPoint2 pos, int width, int height) {
        this(pos.x, pos.y, width, height);
    }
}

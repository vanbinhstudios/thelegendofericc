package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Media;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;

/**
 * A mishmash for prototyping. This class should be broken down as soon as proper Actor systems are introduced.
 */
public class Mob extends Entity {
    public Mob(int x, int y) {
        SpriteSheetComponent renderable = new SpriteSheetComponent(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft);
        renderable.sprite.setOrigin(0, -0.35f);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new DirectionComponent(Direction.DOWN));
        add(new MobComponent());
        add(new CurrentActionComponent(Actions.NOTHING));
    }

    public Mob(GridPoint2 pos) {
        this(pos.x, pos.y);
    }
}

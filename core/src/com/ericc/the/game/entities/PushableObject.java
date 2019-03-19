package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ericc.the.game.Direction;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;

public class PushableObject extends Entity{
    public PushableObject(int x, int y, TextureRegion texture) {
        SpriteSheetComponent renderable = new SpriteSheetComponent(texture);
        renderable.sprite.setOrigin(0, -0.35f);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new DirectionComponent(Direction.DOWN));
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
    }
}

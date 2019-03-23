package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;

public class PushableObject extends Entity {
    public PushableObject(int x, int y, TextureRegion texture) {
        RenderableComponent renderable = new RenderableComponent(Models.crate);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
        add(new OneSidedComponent());
    }

    public PushableObject(GridPoint2 pos, TextureRegion texture) {
        this(pos.x, pos.y, texture);
    }
}

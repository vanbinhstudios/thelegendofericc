package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

public class PushableObject extends Entity {
    public PushableObject(int x, int y, Map map) {
        RenderableComponent renderable = new RenderableComponent(Models.crate);
        add(new PositionComponent(x, y, map));
        add(renderable);
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
        add(new OneSidedComponent());
    }

    public PushableObject(GridPoint2 pos, Map map) {
        this(pos.x, pos.y, map);
    }
}

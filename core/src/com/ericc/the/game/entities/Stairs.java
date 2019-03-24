package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.StaircaseDestination;

public class Stairs extends Entity {
    public Stairs(int x, int y, TextureRegion texture, StaircaseDestination destination) {
        SpriteSheetComponent renderable = new SpriteSheetComponent(texture);
        renderable.sprite.setOrigin(0, -0.35f);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
        add(new OneSidedComponent());
        add(new StaircaseDestinationComponent(destination));
    }

    public Stairs(GridPoint2 pos, TextureRegion texture, StaircaseDestination destination) {
        this(pos.x, pos.y, texture, destination);
    }
}

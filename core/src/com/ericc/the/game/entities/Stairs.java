package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ericc.the.game.components.*;

public class Stairs extends Entity {
    public Stairs(int x, int y, TextureRegion texture, boolean descending) {
        SpriteSheetComponent renderable = new SpriteSheetComponent(texture);
        renderable.sprite.setOrigin(0, -0.35f);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new OneSidedComponent());
        add(descending ? new DescendingComponent() : new AscendingComponent());
    }
}

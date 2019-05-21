package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.components.*;
import com.ericc.the.game.effects.SetAnimation;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class Loot extends Entity {
    public Loot(GridPoint xy, Map map, Item item) {
        add(new PositionComponent(xy, map));
        add(new RenderableComponent(item.model));
        add(new AnimationComponent());
        add(new LootComponent(item));
        new SetAnimation(AnimationState.HOVERING).apply(this, null);
    }
}

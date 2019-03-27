package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

public class Mob extends Entity {
    public Mob(int x, int y, Map map) {
        add(new PositionComponent(x, y, map));
        add(new RenderableComponent(Models.mage));
        add(new MobComponent());
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new IntentionComponent(Actions.NOTHING));
        add(new StatsComponent(45, 30, 20));
        add(new InitiativeComponent());

    }

    public Mob(GridPoint2 pos, Map map) {
        this(pos.x, pos.y, map);
    }
}

package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Media;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;

public class Mob extends Entity {
    public Mob(int x, int y) {
        SpriteSheetComponent renderable = new SpriteSheetComponent(Media.mobBack, Media.mobRight, Media.mobFront, Media.mobLeft);
        renderable.sprite.setOrigin(0, -0.35f);
        add(new PositionComponent(x, y));
        add(renderable);
        add(new DirectionComponent(Direction.DOWN));
        add(new MobComponent());
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new IntentionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
        add(new SentienceComponent());
        add(new AgilityComponent(30));
        add(new HealthComponent(50));
        add(new IntelligenceComponent(45));
        add(new MovementPointsComponent(100));
        add(new StrengthComponent(20));

    }

    public Mob(GridPoint2 pos) {
        this(pos.x, pos.y);
    }
}

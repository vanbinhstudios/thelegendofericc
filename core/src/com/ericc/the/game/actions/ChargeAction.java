package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationState;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.effects.SetAnimation;

public class ChargeAction extends Action {
    private final int delay;

    public ChargeAction(int delay) {
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        new SetAnimation(AnimationState.HOVERING).apply(entity, engine);
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        return pos.map.hasAnimationDependency(pos.xy);
    }
}

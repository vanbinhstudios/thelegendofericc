package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.components.AnimationState;

public class SetAnimation implements Effect {
    private AnimationState state;

    public SetAnimation(AnimationState state) {
        this.state = state;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        AnimationComponent anim = Mappers.animation.get(entity);
        if (anim != null) {
            anim.localTime = 0;
            anim.state = state;
            if (Mappers.renderable.has(entity)) {
                anim.animation = Mappers.renderable.get(entity).model.animationSheet.get(anim.state);
            }
        }
    }
}

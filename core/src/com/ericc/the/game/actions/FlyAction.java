package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.components.AnimationState;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.utils.GridPoint;

public class FlyAction extends Action {
    public int delay;

    public FlyAction(int delay) {
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        if (pos.map.hasAnimationDependency(pos.xy)) {
            return true;
        }

        AnimationComponent animation = Mappers.animation.get(entity);
        if (animation != null && animation.animation.isBlocking() && !animation.animation.isOver(animation.localTime)) {
            return true;
        }

        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);

        Entity subject = pos.map.entityMap.get(pos.xy);

        // Target tile has a hittable entity standing on it (non-player and possessing statistics)
        if (subject != null) {
            if (Mappers.stats.has(subject)) {
                Effects.inflictDamage(subject, Mappers.damage.get(entity).damage);
            }

            Effects.kill(entity);
        } else {
            GridPoint offset = GridPoint.fromDirection(pos.direction);
            if (!pos.map.isFloor(pos.xy.add(GridPoint.fromDirection(pos.direction)))) {
                Effects.kill(entity);
            } else {
                Effects.setAnimation(entity, AnimationState.SLIDING);
                pos.xy = pos.xy.add(offset);
            }
        }
    }
}

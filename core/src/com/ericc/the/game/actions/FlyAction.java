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
        if (animation != null && animation.animation.isBlocking()) {
            return true;
        }

        return false;
    }

    @Override
    public void execute(Entity projectile, Engine engine) {
        PositionComponent pos = Mappers.position.get(projectile);

        Entity target = pos.map.collisionMap.get(pos.xy);

        // Target tile has a hittable entity standing on it (non-player and possessing statistics)
        if (target != null) {
            if (Mappers.stats.has(target)) {
                Effects.inflictDamage(target, Mappers.damage.get(projectile).damage,
                        Mappers.owner.get(projectile).owner);
            }

            Effects.kill(projectile);
        } else {
            GridPoint offset = GridPoint.fromDirection(pos.dir);
            GridPoint newPos = pos.xy.add(offset);
            if (!pos.map.isFloor(newPos)) {
                Effects.kill(projectile);
            } else {
                Effects.setAnimation(projectile, AnimationState.SLIDING);
                pos.xy = newPos;
            }
        }
    }
}

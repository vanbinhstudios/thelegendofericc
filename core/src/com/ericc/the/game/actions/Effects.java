package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;
import com.ericc.the.game.utils.GridPoint;

public class Effects {
    public static void moveBy(Entity entity, GridPoint offset) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.map.entityMap.remove(pos.xy);
        pos.xy = pos.xy.add(offset);
        pos.map.entityMap.put(pos.xy, entity);
        entity.add(DirtyFlag.DIRTY);
    }

    public static void rotateTo(Entity entity, Direction dir) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.direction = dir;
    }

    public static void inflictDamage(Entity entity, int damage) {
        StatsComponent stats = Mappers.stats.get(entity);
        stats.health -= damage;
        if (stats.health <= 0) {
            kill(entity);
        }
    }

    public static void kill(Entity entity) {
        entity.remove(AgencyComponent.class);
        entity.remove(CollisionComponent.class);
        entity.remove(AttackComponent.class);
        setAnimation(entity, AnimationState.DYING);
    }

    public static void setAnimation(Entity entity, AnimationState state) {
        AnimationComponent anim = Mappers.animation.get(entity);
        anim.localTime = 0;
        anim.state = state;
        if (Mappers.renderable.has(entity)) {
            anim.animation = Mappers.renderable.get(entity).model.animationSheet.get(anim.state);
        }
    }
}

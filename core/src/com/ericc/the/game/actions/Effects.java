package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;
import com.ericc.the.game.ui.actors.Popup;
import com.ericc.the.game.utils.GridPoint;

public class Effects {
    public static void moveBy(Entity entity, GridPoint offset) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.map.collisionMap.remove(pos.xy);
        pos.xy = pos.xy.add(offset);
        pos.map.collisionMap.put(pos.xy, entity);

        Entity trap = pos.map.trapMap.get(pos.xy);
        if (trap != null) {
            trap.add(ActivatedComponent.ACTIVE);
        }

        entity.add(DirtyFlag.DIRTY);
    }

    public static void rotateTo(Entity entity, Direction dir) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.dir = dir;
    }

    public static void inflictDamage(Entity target, int damage, Entity attackOwner) {
        StatsComponent targetStats = Mappers.stats.get(target);
        targetStats.health -= damage;
        if (targetStats.health <= 0) {
            if (attackOwner != null
                    && Mappers.experienceWorth.has(target)
                    && Mappers.stats.has(attackOwner)) {
                increaseExperience(attackOwner, Mappers.experienceWorth.get(target).experienceWorth);
            }

            kill(target);
        }
    }

    public static void increaseExperience(Entity target, int experienceAmount) {
        StatsComponent stats = Mappers.stats.get(target);
        stats.experience += experienceAmount;
        // TODO A proper levelling system
        while (stats.experience > stats.maxExperience) {
            stats.level++;
            stats.experience -= stats.maxExperience;
            stats.maxExperience *= 1.5;
            stats.health += Math.min(stats.level * 25, stats.maxHealth - stats.health);
            stats.mana += Math.min(stats.level * 25, stats.maxMana - stats.mana);

            if (Mappers.player.has(target)) {
                if (stats.level == 2) {
                    Popup.show("You unlocked FIRE BEAM");
                } else if (stats.level == 4) {
                    // TODO change name
                    Popup.show("You unlocked PIERDZENIE");
                } else {
                    Popup.show("No skills gained this time");
                }
            }
        }
    }

    public static void kill(Entity entity) {
        entity.remove(AgencyComponent.class);
        entity.remove(CollisionComponent.class);
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
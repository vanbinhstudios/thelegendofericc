package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.FlyAction;
import com.ericc.the.game.animations.Animations;
import com.ericc.the.game.animations.DeathAnimation;
import com.ericc.the.game.components.*;
import com.ericc.the.game.utils.GridPoint;

public class ProjectileSystem extends IteratingSystem {
    public ProjectileSystem(int priority) {
        super(Family.all(PositionComponent.class, FlyAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);

        Entity subject = pos.map.entityMap.get(pos.xy);

        if (pos.map.hasAnimationDependency(pos.xy)) {
            entity.add(SyncComponent.SYNC);
            return;
        }

        // Target tile has a hittable entity standing on it (non-player and possessing statistics)
        if (subject != null) {
            if (Mappers.stats.has(subject)) {
                StatsComponent stats = Mappers.stats.get(subject);

                System.out.print("Initial HP: " + stats.health + " ");
                int damage = Mappers.damage.get(entity).damage;
                stats.health -= damage;

                System.out.print("Final HP: " + stats.health + "\n");

                if (stats.health <= 0) {
                    subject.add(new AnimationComponent(
                            new DeathAnimation(1 / 0.8f, true, 0.5f)));
                    subject.add(new DeathComponent());
                }
            }

            entity.add(new AnimationComponent(new DeathAnimation(1 / 0.3f, false, 1.0f)));
            entity.add(new DeathComponent());
        } else {
            AnimationComponent animation = Mappers.animation.get(entity);
            if (animation != null && animation.animation.isBlocking() && !animation.animation.isOver()) {
                entity.add(SyncComponent.SYNC);
                return;
            }

            GridPoint offset = GridPoint.fromDirection(pos.direction);
            if (!pos.map.isFloor(pos.xy.add(GridPoint.fromDirection(pos.direction)))) {
                entity.add(new AnimationComponent(new DeathAnimation(1 / 0.3f, false, 1.0f)));
                entity.add(new DeathComponent());
            } else {
                entity.add(new AnimationComponent(Animations.SLIDE(pos.direction, 20)));
                pos.xy = pos.xy.add(offset);
            }
        }

        entity.remove(FlyAction.class);
    }
}

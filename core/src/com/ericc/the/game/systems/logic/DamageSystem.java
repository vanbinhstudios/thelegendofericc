package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;

public class DamageSystem extends IteratingSystem {
    public DamageSystem(int priority) {
        super(Family.all(PositionComponent.class, AttackComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity attack, float deltaTime) {
        PositionComponent pos = Mappers.position.get(attack);
        Entity subject = pos.map.entityMap.get(pos.xy);

        if (subject != null && !Mappers.player.has(subject)) {
            subject.remove(CollisionComponent.class);
            subject.remove(AgencyComponent.class);
            subject.add(new DeathComponent(1 / 0.8f, true, 0.5f));
        }
        attack.remove(AttackComponent.class);
        attack.add(new DeathComponent(1 / 0.3f, false, 1.0f));
    }
}
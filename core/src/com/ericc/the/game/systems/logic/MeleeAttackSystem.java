package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.AttackAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.entities.Attack;
import com.ericc.the.game.utils.GridPoint;

public class MeleeAttackSystem extends IteratingSystem {
    public MeleeAttackSystem(int priority) {
        super(Family.all(PositionComponent.class, AttackAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        AttackAction move = Mappers.attack.get(entity);

        pos.direction = move.direction;

        GridPoint attackPosition = pos.xy.add(GridPoint.fromDirection(move.direction));

        if (pos.map.hasAnimationDependency(attackPosition)) {
            entity.add(SyncComponent.SYNC);
            return;
        }

        getEngine().addEntity(new Attack(attackPosition, pos.map, move.direction));

        entity.remove(AttackAction.class);
    }
}

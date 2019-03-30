package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.AttackAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.entities.Attack;

public class MeleeAttackSystem extends IteratingSystem {
    public MeleeAttackSystem(int priority) {
        super(Family.all(PositionComponent.class, AttackAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        AttackAction move = Mappers.attack.get(entity);

        pos.direction = move.direction;

        if (move.direction == Direction.LEFT) {
            getEngine().addEntity(new Attack(pos.xy.shift(-1, 0), pos.map, move.direction));
        } else if (move.direction == Direction.RIGHT) {
            getEngine().addEntity(new Attack(pos.xy.shift(1, 0), pos.map, move.direction));
        } else if (move.direction == Direction.UP) {
            getEngine().addEntity(new Attack(pos.xy.shift(0, 1), pos.map, move.direction));
        } else {
            getEngine().addEntity(new Attack(pos.xy.shift(0, -1), pos.map, move.direction));
        }

        entity.remove(AttackAction.class);
    }
}

package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.AffineAnimationComponent;
import com.ericc.the.game.components.PositionComponent;

public class MovementSystem extends IteratingSystem {
    public MovementSystem(int priority) {
        super(Family.all(PositionComponent.class, MovementAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        MovementAction move = Mappers.movementAction.get(entity);

        int dx = 0;
        int dy = 0;

        pos.direction = move.direction;

        if (move.direction == Direction.LEFT && pos.map.isPassable(pos.getX() - 1, pos.getY())) {
            dx = -1;
        } else if (move.direction == Direction.RIGHT && pos.map.isPassable(pos.getX() + 1, pos.getY())) {
            dx = 1;
        } else if (move.direction == Direction.UP && pos.map.isPassable(pos.getX(), pos.getY() + 1)) {
            dy = 1;
        } else if (move.direction == Direction.DOWN && pos.map.isPassable(pos.getX(), pos.getY() - 1)) {
            dy = -1;
        }

        if (dy != 0 || dx != 0) {
            pos.map.entityMap.remove(pos.xy);
            entity.add(new AffineAnimationComponent(new JumpAnimation(new Vector2(dx, dy), 0.6f, 0.15f)));
            pos.xy = pos.xy.shift(dx, dy);
            pos.map.entityMap.put(pos.xy, entity);
        }

        entity.remove(MovementAction.class);
    }
}

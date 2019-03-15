package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> movables;
    private Map map;

    public MovementSystem(Map map) {
        this.map = map;
    }

    @Override
    public void addedToEngine(Engine engine) {
        movables = engine.getEntitiesFor(Family.all(DirectionComponent.class,
                PositionComponent.class, CurrentActionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : movables) {
            PositionComponent pos = Mappers.position.get(entity);
            DirectionComponent dir = Mappers.direction.get(entity);
            CurrentActionComponent action = Mappers.currentAction.get(entity);

            int dx = 0;
            int dy = 0;

            if (action.action instanceof MovementAction) {
                MovementAction move = (MovementAction) action.action;

                if (move.direction == Direction.LEFT) {
                    if (map.isPassable(pos.x - 1, pos.y)) {
                        dx = -1;
                    }
                } else if (move.direction == Direction.RIGHT) {
                    if (map.isPassable(pos.x + 1, pos.y)) {
                        dx = 1;
                    }
                } else if (move.direction == Direction.UP) {
                    if (map.isPassable(pos.x, pos.y + 1)) {
                        dy = 1;
                    }
                } else {
                    if (map.isPassable(pos.x, pos.y - 1)) {
                        dy = -1;
                    }
                }

                dir.direction = move.direction;

                if (dy != 0 || dx != 0) {
                    entity.add(new AffineAnimationComponent(new JumpAnimation(new Vector2(dx, dy), 0.6f, 0.15f)));
                    pos.x += dx;
                    pos.y += dy;
                }
            }
        }
    }
}

package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> directableMovables;
    private ImmutableArray<Entity> nonDirectableMovables;
    private Map map;

    public MovementSystem(Map map) {
        super(2); // Depends on ActionHandlingSystem
        this.map = map;
    }

    @Override
    public void addedToEngine(Engine engine) {
        directableMovables = engine.getEntitiesFor(Family.all(PositionComponent.class,
                CurrentActionComponent.class, DirectionComponent.class).get());
        nonDirectableMovables = engine.getEntitiesFor(Family.all(PositionComponent.class,
                CurrentActionComponent.class, OneSidedComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        moveDirectables();
        moveNonDirectables();
    }

    private void moveDirectables() {
        for (Entity entity : directableMovables) {
            CurrentActionComponent action = Mappers.currentAction.get(entity);

            if (action.action instanceof MovementAction) {
                PositionComponent pos = Mappers.position.get(entity);
                MovementAction move = (MovementAction) action.action;

                DirectionComponent dir = Mappers.direction.get(entity);
                dir.direction = move.direction;

                setCoordinates(entity, pos, action, move);
                action.action = Actions.NOTHING;
            }
        }
    }

    private void moveNonDirectables() {
        for (Entity entity : nonDirectableMovables) {
            CurrentActionComponent action = Mappers.currentAction.get(entity);

            if (action.action instanceof MovementAction) {
                PositionComponent pos = Mappers.position.get(entity);
                MovementAction move = (MovementAction) action.action;

                setCoordinates(entity, pos, action, move);
                action.action = Actions.NOTHING;
            }
        }
    }

    private void setCoordinates(Entity entity, PositionComponent pos, CurrentActionComponent action, MovementAction move) {
        int dx = 0;
        int dy = 0;


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
        } else { // if (move.direction == Direction.DOWN)
            if (map.isPassable(pos.x, pos.y - 1)) {
                dy = -1;
            }
        }

        animate(entity, pos, dx, dy);
    }

    private void animate(Entity entity, PositionComponent pos, int dx, int dy) {
        if (dy != 0 || dx != 0) {
            entity.add(new AffineAnimationComponent(new JumpAnimation(new Vector2(dx, dy), 0.6f, 0.15f)));
            pos.x += dx;
            pos.y += dy;
        }
    }
}

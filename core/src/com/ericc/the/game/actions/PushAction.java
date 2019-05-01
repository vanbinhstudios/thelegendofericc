package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationState;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.utils.GridPoint;

import java.util.Stack;

public class PushAction extends Action {
    public Direction direction;
    public int delay;

    public PushAction(Direction direction, int delay) {
        this.direction = direction;
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        GridPoint offset = GridPoint.fromDirection(direction);

        GridPoint start = pos.xy;
        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (pos.map.hasAnimationDependency(start)) {
                return true;
            }
            start = start.add(offset);
        }

        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);

        if (!canPush(pos)) {
            return;
        }

        Stack<Entity> stack = getPushedEntities(pos);

        GridPoint offset = GridPoint.fromDirection(direction);

        while (!stack.empty()) {
            Entity subject = stack.pop();
            Effects.rotateTo(subject, direction);
            Effects.setAnimation(subject, AnimationState.PUSHING);
            Effects.moveBy(subject, offset);
        }

        Effects.rotateTo(entity, direction);
        Effects.setAnimation(entity, AnimationState.PUSHING);
        Effects.moveBy(entity, offset);
    }

    private boolean canPush(PositionComponent pos) {
        GridPoint fromPush = GridPoint.fromDirection(direction);
        GridPoint start = pos.xy.add(fromPush);

        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (Mappers.hostile.has(pos.map.getEntity(start))) {
                return false;
            }

            start = start.add(fromPush);
        }

        return pos.map.isPassable(start);
    }

    private Stack<Entity> getPushedEntities(PositionComponent pos) {
        Stack<Entity> stack = new Stack<>();
        GridPoint fromPush = GridPoint.fromDirection(direction);
        GridPoint start = pos.xy.add(fromPush);

        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (Mappers.hostile.has(pos.map.getEntity(start))) {
                break;
            }

            stack.add(pos.map.getEntity(start));
            start = start.add(fromPush);
        }
        return stack;
    }
}

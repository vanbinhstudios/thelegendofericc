package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.AnimationState;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.utils.GridPoint;

public class MovementAction extends Action {
    public Direction direction;
    public int delay;
    public MovementType type;

    public MovementAction(Direction direction, int delay, MovementType type) {
        this.direction = direction;
        this.delay = delay;
        this.type = type;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        return pos.map.hasAnimationDependency(pos.xy);
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.dir = direction;

        if (pos.map.isPassable(pos.xy.add(GridPoint.fromDirection(pos.dir)))) {
            GridPoint offset = GridPoint.fromDirection(pos.dir);
            Effects.moveBy(entity, offset);
            AnimationState state =
                    (type == MovementType.WALK) ? AnimationState.WALKING : AnimationState.RUNNING;
            Effects.setAnimation(entity, state);
        }
    }

    public enum MovementType {
        WALK, RUN
    }
}

package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class MovementAction extends Action {
    public Direction direction;
    public int delay;
    public MovementType type;

    public MovementAction(Direction direction, int delay, MovementType type) {
        this.direction = direction;
        this.delay = delay;
        this.type = type;
    }

    public enum MovementType {
        CASUAL, RUN, PUSH
    }

    @Override
    public int getDelay() {
        return delay;
    }
}

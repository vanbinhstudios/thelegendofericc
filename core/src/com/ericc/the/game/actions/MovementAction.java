package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class MovementAction extends Action {
    public Direction direction;
    public int delay;
    public MOVEMENT_TYPE type;

    public MovementAction(Direction direction, int delay, MOVEMENT_TYPE type) {
        this.direction = direction;
        this.delay = delay;
        this.type = type;
    }

    public enum MOVEMENT_TYPE {
        CASUAL, RUN, PUSH
    }

    @Override
    public int getDelay() {
        return delay;
    }
}

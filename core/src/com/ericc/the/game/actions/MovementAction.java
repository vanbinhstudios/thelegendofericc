package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class MovementAction extends Action {
    public Direction direction;
    public int delay;

    public MovementAction(Direction direction, int delay) {
        this.direction = direction;
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}

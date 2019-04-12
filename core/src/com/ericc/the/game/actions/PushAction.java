package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

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
}

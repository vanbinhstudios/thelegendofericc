package com.ericc.the.game.actions;

public class FlyAction extends Action {
    public int delay;

    public FlyAction(int delay) {
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}

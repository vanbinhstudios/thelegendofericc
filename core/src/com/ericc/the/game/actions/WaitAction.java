package com.ericc.the.game.actions;

public class WaitAction extends Action {
    private final int delay;

    public WaitAction(int delay) {
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}

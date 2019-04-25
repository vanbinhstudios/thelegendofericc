package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class WaitAction extends Action {
    private final int delay;

    public WaitAction(int delay) {
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        return false;
    }
}

package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.effects.Kill;

public class DieAction extends Action {
    @Override
    public int getDelay() {
        return 100000;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        new Kill(null).apply(entity, engine);
    }
}

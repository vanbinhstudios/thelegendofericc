package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public abstract class Action {
    public abstract int getDelay();

    public abstract boolean needsSync(Entity entity, Engine engine);

    public abstract void execute(Entity entity, Engine engine);
}

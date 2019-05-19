package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public interface Effect {
    void apply(Entity entity, Engine engine);
}
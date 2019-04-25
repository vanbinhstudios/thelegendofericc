package com.ericc.the.game.animations;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.RenderableComponent;

public class IdleAnimation implements Animation {
    @Override
    public void apply(Direction dir, float deltaTime, RenderableComponent renderable) {
        renderable.transform.idt();
    }

    @Override
    public boolean isOver(float deltaTime) {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}

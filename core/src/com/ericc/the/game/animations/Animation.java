package com.ericc.the.game.animations;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.RenderableComponent;

public interface Animation {
    void apply(Direction dir, float deltaTime, RenderableComponent renderable);

    boolean isOver(float delta);

    boolean isBlocking();
}

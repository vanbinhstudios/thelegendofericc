package com.ericc.the.game.animations;

import com.ericc.the.game.components.RenderableComponent;

public interface Animation {
    void update(float deltaTime);
    void apply(RenderableComponent renderable);
    boolean isOver();
    boolean isBlocking();
}

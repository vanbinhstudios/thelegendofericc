package com.ericc.the.game.animations;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.RenderableComponent;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class DeathAnimation implements Animation {
    private float fadingSpeed;
    private boolean desaturate;
    private float initialAlpha;

    public DeathAnimation(float fadingSpeed, boolean desaturate, float initialAlpha) {
        this.fadingSpeed = fadingSpeed;
        this.desaturate = desaturate;
        this.initialAlpha = initialAlpha;
    }

    private float converge(float current, float target, float delta) {
        if (current < target) {
            return min(current + delta, target);
        } else {
            return max(current - delta, target);
        }
    }

    @Override
    public void apply(Direction direction, float deltaTime, RenderableComponent renderable) {
        float alpha = min(renderable.alpha, initialAlpha);
        alpha = converge(alpha, 0, deltaTime * fadingSpeed);

        renderable.alpha = alpha;
        if (desaturate) {
            renderable.saturation = 0f;
        }
    }

    @Override
    public boolean isOver(float time) {
        return time * fadingSpeed > initialAlpha;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}

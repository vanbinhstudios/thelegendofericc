package com.ericc.the.game.animations;

import com.ericc.the.game.components.RenderableComponent;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class DeathAnimation implements Animation {
    private float fadingSpeed;
    private boolean desaturate;
    private float initialAlpha;
    private float saturation;
    private float alpha;

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
    public void update(float deltaTime) {
        if (desaturate) {
            saturation = 0f;
        }
        alpha = min(alpha, initialAlpha);
        alpha = converge(alpha, 0, deltaTime * fadingSpeed);
    }

    @Override
    public void apply(RenderableComponent renderable) {
        renderable.alpha = alpha;
        renderable.saturation = saturation;
    }

    @Override
    public boolean isOver() {
        return alpha == 0;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}

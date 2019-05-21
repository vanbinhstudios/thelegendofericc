package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.components.RenderableComponent;

/**
 * A simple parabolic jump.
 * <p>
 * The transform is applied to the entity in it's local space: that is, the entity's origin
 * is the pivot of the transform.
 * Caveat: when an entity changes its logical position, the move state follows *afterwards*!
 * As such, those animations should return transforms relative to their *ending* state!
 */
public class HoverAnimation implements Animation {
    private final Affine2 transform = new Affine2();
    private float amplitude;
    private float period;

    public HoverAnimation(float amplitude, float period) {
        this.amplitude = amplitude;
        this.period = period;
    }

    @Override
    public void apply(Direction dir, float time, RenderableComponent renderable) {
        transform.idt();
        transform.translate(0, amplitude * (1 + (float) -Math.cos(time / period * 2 * Math.PI)));
        renderable.transform.set(transform);
    }

    @Override
    public boolean isOver(float time) {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}

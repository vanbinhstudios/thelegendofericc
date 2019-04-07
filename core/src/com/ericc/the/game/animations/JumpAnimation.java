package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.components.RenderableComponent;

import static java.lang.Float.min;

/**
 * A simple parabolic jump.
 * <p>
 * The transform is applied to the entity in it's local space: that is, the entity's origin
 * is the pivot of the transform.
 * Caveat: when an entity changes its logical position, the move animation follows *afterwards*!
 * As such, those animations should return transforms relative to their *ending* state!
 */
public class JumpAnimation implements Animation {
    private Vector2 startPosition;
    private float time;
    private float duration;
    private float height;
    private final Affine2 transform = new Affine2();
    private boolean done = false;

    /**
     * @param transition the vector from start to end position
     */
    public JumpAnimation(Vector2 transition, float height, float duration) {
        this.startPosition = transition.scl(-1); // Start here, land at (0, 0)
        this.duration = duration;
        this.height = height;
        this.time = 0;
    }

    @Override
    public void update(float deltaTime) {
        time += deltaTime;
        transform.idt();
        float timeFraction = time / duration;

        if (timeFraction > 1) {
            done = true;
            return;
        }

        // Horizontal velocity is constant.
        Vector2 currentPosition = startPosition.cpy().interpolate(Vector2.Zero, timeFraction, Interpolation.linear);
        transform.setToTranslation(currentPosition);

        // Interpolation.pow2Out describes a half-parabola. We get the second half by symmetry.
        float heightTimeFraction = min(timeFraction, 1 - timeFraction);
        float currentHeight = height * Interpolation.pow2Out.apply(heightTimeFraction);
        transform.translate(0, currentHeight);
    }

    @Override
    public void apply(RenderableComponent renderable) {
        renderable.transform.set(transform);
    }

    @Override
    public boolean isOver() {
        return done;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}

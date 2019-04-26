package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.utils.GridPoint;

import static java.lang.Float.min;

/**
 * A simple parabolic jump.
 * <p>
 * The transform is applied to the entity in it's local space: that is, the entity's origin
 * is the pivot of the transform.
 * Caveat: when an entity changes its logical position, the move state follows *afterwards*!
 * As such, those animations should return transforms relative to their *ending* state!
 */
public class JumpAnimation implements Animation {
    private final Affine2 transform = new Affine2();
    private final Vector2 startPosition = new Vector2();
    private GridPoint offset;
    private float duration;
    private float height;

    public JumpAnimation(float height, float duration) {
        this.duration = duration;
        this.height = height;
    }

    @Override
    public void apply(Direction dir, float time, RenderableComponent renderable) {
        offset = GridPoint.fromDirection(dir);
        startPosition.set(-offset.x, -offset.y); // Start here, land at (0, 0)

        transform.idt();
        float timeFraction = time / duration;

        if (timeFraction > 1) {
            timeFraction = 1;
        }

        // Horizontal velocity is constant.
        Vector2 currentPosition = startPosition.cpy().interpolate(Vector2.Zero, timeFraction, Interpolation.linear);
        transform.setToTranslation(currentPosition);

        // Interpolation.pow2Out describes a half-parabola. We get the second half by symmetry.
        float heightTimeFraction = min(timeFraction, 1 - timeFraction);
        float currentHeight = height * Interpolation.pow2Out.apply(heightTimeFraction);
        transform.translate(0, currentHeight);

        renderable.transform.set(transform);
    }

    @Override
    public boolean isOver(float time) {
        return time > duration;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}

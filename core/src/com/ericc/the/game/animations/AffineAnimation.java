package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Affine2;

/**
 * An affine animation is the most basic kind of animation - a composition of rotation, translation and scaling.
 * We define it as a function of time (measured from the start of the animation),
 * returning an affine transformation matrix.
 * <p>
 * It can be attached to a renderable entity through the AffineAnimationComponent.
 * <p>
 * The transform is applied to the entity in it's local space: that is, the entity's origin
 * is the pivot of the transform.
 * Caveat: when an entity changes its logical position, the move animation follows *afterwards*!
 * As such, those animations should return transforms relative to their *ending* state!
 */
public interface AffineAnimation {
    public Affine2 getTransform(float time);
}

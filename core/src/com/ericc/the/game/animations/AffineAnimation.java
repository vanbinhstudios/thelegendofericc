package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Affine2;

public interface AffineAnimation {
    // The frame of reference is centered at the final position of the sprite origin.
    public Affine2 getTransform(float time);
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.animations.AffineAnimation;

/**
 * See {@link AffineAnimation}
 */
public class AffineAnimationComponent implements Component {
    private float time = 0; // Time since animation start.
    private AffineAnimation animationFunction;

    public AffineAnimationComponent(AffineAnimation animationFunction) {
        this.animationFunction = animationFunction;
    }

    public void update(float dt) {
        time += dt;
    }

    public Affine2 getTransform() {
        return animationFunction.getTransform(time);
    }
}

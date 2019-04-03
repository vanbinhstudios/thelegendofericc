package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.animations.Animation;

public class AnimationComponent implements Component {
    public final Animation animation;

    public AnimationComponent(Animation animation) {
        this.animation = animation;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.animations.Animation;
import com.ericc.the.game.animations.Animations;

public class AnimationComponent implements Component {
    public AnimationState state = AnimationState.IDLE;
    public float localTime;
    public Animation animation = Animations.IDLE;
}

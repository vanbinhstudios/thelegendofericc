package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class DeathComponent implements Component {
    public float fadingSpeed;
    public boolean desaturate;
    public float initialAlpha;

    public DeathComponent(float fadingSpeed, boolean desaturate, float initialAlpha) {
        this.fadingSpeed = fadingSpeed;
        this.desaturate = desaturate;
        this.initialAlpha = initialAlpha;
    }
}

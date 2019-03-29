package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraComponent implements Component {
    public Viewport viewport;
    public int top, left, bottom, right;

    public CameraComponent(Viewport viewport) {
        this.viewport = viewport;
    }
}

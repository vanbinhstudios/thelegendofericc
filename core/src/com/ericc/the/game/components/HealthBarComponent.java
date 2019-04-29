package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class HealthBarComponent extends RenderableComponent implements Component {
    public HealthBarComponent(Model model) {
        super(model);
        this.zOrder = 0;
    }
}

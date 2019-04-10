package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class HealthbarComponent extends RenderableComponent implements Component{
    public HealthbarComponent(Model model) {
        super(model);
        this.zOrder = -10;
    }
}

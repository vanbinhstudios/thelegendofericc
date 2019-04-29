package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class ExperienceBarComponent extends RenderableComponent implements Component {
    public ExperienceBarComponent(Model model) {
        super(model);
        this.zOrder = 0;
    }
}

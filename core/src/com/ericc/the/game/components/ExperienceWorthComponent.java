package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class ExperienceWorthComponent implements Component {
    public int experienceWorth;

    public ExperienceWorthComponent(int experienceWorth) {
        this.experienceWorth = experienceWorth;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class AgilityComponent implements Component {
    public int value;

    public AgilityComponent(int value) {
        this.value = value;
    }
}

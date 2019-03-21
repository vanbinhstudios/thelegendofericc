package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class IntelligenceComponent implements Component {
    public int value;

    public IntelligenceComponent(int value) {
        this.value = value;
    }
}

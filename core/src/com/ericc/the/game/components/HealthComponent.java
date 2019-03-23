package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    public int value;

    public HealthComponent(int value) {
        this.value = value;
    }
}

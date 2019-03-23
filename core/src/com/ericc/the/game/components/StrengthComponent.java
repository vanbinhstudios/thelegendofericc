package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class StrengthComponent implements Component {
    public int value;

    public StrengthComponent(int value) {
        this.value = value;
    }
}

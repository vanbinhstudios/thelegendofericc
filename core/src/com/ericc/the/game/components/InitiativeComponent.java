package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class InitiativeComponent implements Component {
    public int value;

    public InitiativeComponent() {
        this.value = 0;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class MovementPointsComponent implements Component {
    public int value;

    public MovementPointsComponent(int value) {
        this.value = value;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.Direction;

public class DirectionComponent implements Component {
    public Direction direction;

    public DirectionComponent(Direction direction) {
        this.direction = direction;
    }
}

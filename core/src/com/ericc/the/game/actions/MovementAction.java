package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class MovementAction extends Action {
    public Direction direction;

    public MovementAction(Direction direction) {
        this.direction = direction;
    }
}

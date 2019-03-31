package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class MovementAction extends Action {
    public Direction direction;
    public int timeCost;

    public MovementAction(Direction direction, int timeCost) {
        this.direction = direction;
        this.timeCost = timeCost;
    }

    @Override
    public int getBaseTimeCost() {
        return timeCost;
    }
}

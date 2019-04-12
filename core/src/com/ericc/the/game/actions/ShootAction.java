package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class ShootAction extends Action {
    public Direction direction;
    public int power;

    public ShootAction(Direction direction, int power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public int getDelay() {
        return 100;
    }
}

package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.utils.GridPoint;

public class Actions {
    public static WaitAction WAIT = new WaitAction(100);
    public static WaitAction LONG_WAIT = new WaitAction(500);
    public static WaitAction NOTHING = new WaitAction(0);

    public static MovementAction MOVE(Direction direction) {
        return new MovementAction(direction, 100);
    }

    public static MovementAction RUN(Direction direction) {
        return new MovementAction(direction, 50);
    }

    public static AttackAction ATTACK(Direction direction) {
        return new AttackAction(direction);
    }

    public static AreaOfEffectAttackAction AOEATTACK(GridPoint relativePosition, Model model, Direction direction,
                                                     int width, int height, int delay, int power) {
        return new AreaOfEffectAttackAction(relativePosition, model, direction, width, height, delay, power);
    }
}

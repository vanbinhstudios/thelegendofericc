package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.utils.GridPoint;

public class Actions {
    public static WaitAction WAIT = new WaitAction(100);
    public static WaitAction LONG_WAIT = new WaitAction(500);
    public static WaitAction NOTHING = new WaitAction(0);

    public static MovementAction MOVE(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MovementType.CASUAL);
    }

    public static MovementAction RUN(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MovementType.RUN);
    }

    public static MovementAction PUSH(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MovementType.PUSH);
    }

    public static AreaOfEffectAttackAction AOE_ATTACK(GridPoint relativePosition, Model model, Direction direction,
                                                      int width, int height, int delay, int power) {
        return new AreaOfEffectAttackAction(relativePosition, model, direction, width, height, delay, power);
    }

    public static AreaOfEffectAttackAction DIRECTED_AOE_ATTACK(Model model, Direction direction,
                                                               int width, int height, int delay, int power) {
        return new AreaOfEffectAttackAction(model, direction, width, height, delay, power);
    }

    public static PushAction PUSH(Direction direction) {
        return new PushAction(direction, 150);
    }

    public static FlyAction FLY(int delay) {
        return new FlyAction(delay);
    }

    public static ShootAction SHOOT(Direction direction, int power) {
        return new ShootAction(direction, power);
    }
}

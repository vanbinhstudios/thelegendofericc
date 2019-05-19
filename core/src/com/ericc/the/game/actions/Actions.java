package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.utils.GridPoint;

import java.util.List;

public class Actions {
    public static WaitAction WAIT = new WaitAction(100);
    public static WaitAction LONG_WAIT = new WaitAction(500);
    public static WaitAction NOTHING = new WaitAction(1);

    public static MovementAction MOVE(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MovementType.WALK);
    }

    public static MovementAction RUN(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MovementType.RUN);
    }

    public static AOEAttack AOE_ATTACK(Model model, List<GridPoint> area, Direction dir, int delay, int power) {
        return new AOEAttack(model, area, dir, delay, power);
    }

    public static AOEAttack AOE_ATTACK(Model model, List<GridPoint> area, int delay, int power) {
        return new AOEAttack(model, area, Direction.UP, delay, power);
    }

    public static PushAction PUSH(Direction direction, int delay) {
        return new PushAction(direction, delay);
    }

    public static FlyAction FLY(int delay) {
        return new FlyAction(delay);
    }

    public static ShootAction SHOOT(Direction direction, int delay, int power) {
        return new ShootAction(direction, delay, power);
    }
}

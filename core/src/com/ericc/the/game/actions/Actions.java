package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.utils.GridPoint;

public class Actions {
    public static WaitAction WAIT = new WaitAction(100);
    public static WaitAction LONG_WAIT = new WaitAction(500);
    public static WaitAction NOTHING = new WaitAction(0);

    public static MovementAction MOVE(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MOVEMENT_TYPE.CASUAL);
    }

    public static MovementAction RUN(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MOVEMENT_TYPE.RUN);
    }

    public static MovementAction PUSH(Direction direction, int delay) {
        return new MovementAction(direction, delay, MovementAction.MOVEMENT_TYPE.PUSH);
    }

    public static AreaOfEffectAttackAction AOEATTACK(GridPoint relativePosition, Model model, Direction direction,
                                                     int width, int height, int delay, int power) {
        return new AreaOfEffectAttackAction(relativePosition, model, direction, width, height, delay, power);
    }

    public static AreaOfEffectAttackAction DIRECTEDAOEATTACK(Model model, Direction direction, int width, int height, int delay, int power) {
        return new AreaOfEffectAttackAction(model, direction, width, height, delay, power);
    }

    public static PushAction PUSH(Direction direction) {
        return new PushAction(direction, 150);
    }
}

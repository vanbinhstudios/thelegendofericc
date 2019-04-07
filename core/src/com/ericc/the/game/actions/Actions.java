package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class Actions {
    public static MovementAction MOVE_LEFT = new MovementAction(Direction.LEFT, 100);
    public static MovementAction MOVE_RIGHT = new MovementAction(Direction.RIGHT, 100);
    public static MovementAction MOVE_DOWN = new MovementAction(Direction.DOWN, 100);
    public static MovementAction MOVE_UP = new MovementAction(Direction.UP, 100);
    public static MovementAction RUN_LEFT = new MovementAction(Direction.LEFT, 50);
    public static MovementAction RUN_RIGHT = new MovementAction(Direction.RIGHT, 50);
    public static MovementAction RUN_DOWN = new MovementAction(Direction.DOWN, 50);
    public static MovementAction RUN_UP = new MovementAction(Direction.UP, 50);
    public static AttackAction ATTACK_LEFT = new AttackAction(Direction.LEFT);
    public static AttackAction ATTACK_RIGHT = new AttackAction(Direction.RIGHT);
    public static AttackAction ATTACK_DOWN = new AttackAction(Direction.DOWN);
    public static AttackAction ATTACK_UP = new AttackAction(Direction.UP);
    public static WaitAction WAIT = new WaitAction(100);
    public static WaitAction LONG_WAIT = new WaitAction(500);
}

package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;

public class Actions {
    public static MovementAction MOVE_LEFT = new MovementAction(Direction.LEFT);
    public static MovementAction MOVE_RIGHT = new MovementAction(Direction.RIGHT);
    public static MovementAction MOVE_DOWN = new MovementAction(Direction.DOWN);
    public static MovementAction MOVE_UP = new MovementAction(Direction.UP);
    public static NoAction NOTHING = new NoAction();
}

package com.ericc.the.game.animations;

import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.utils.GridPoint;

public class Animations {
    private static JumpAnimation JUMP(Direction dir, float height, int delay) {
        GridPoint offset = GridPoint.fromDirection(dir);

        return new JumpAnimation(
                new Vector2(offset.x, offset.y),
                height * delay / 100,
                0.15f * delay / 100
        );
    }

    private static JumpAnimation CASUAL_MOVE(Direction dir, int delay) {
        return JUMP(dir, .6f, delay);
    }

    private static JumpAnimation RUN(Direction dir, int delay) {
        return JUMP(dir, .4f, delay);
    }

    private static JumpAnimation PUSH(Direction dir, int delay) {
        return JUMP(dir, .1f, delay);
    }

    public static JumpAnimation MOVE_ANIMATION(MovementAction action) {
        switch (action.type) {
            case CASUAL:
                return CASUAL_MOVE(action.direction, action.delay);
            case RUN:
                return RUN(action.direction, action.delay);
            case PUSH:
                return PUSH(action.direction, action.delay);
        }

        return CASUAL_MOVE(action.direction, action.delay);
    }
}

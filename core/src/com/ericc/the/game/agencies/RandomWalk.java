package com.ericc.the.game.agencies;

import com.ericc.the.game.Direction;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalk implements Agency {

    // TODO this needs to adjust according to stats component, so that delay is not a CONST
    static private final Action[] actions = {
            Actions.MOVE(Direction.UP, 100),
            Actions.MOVE(Direction.RIGHT, 100),
            Actions.MOVE(Direction.LEFT, 100),
            Actions.MOVE(Direction.DOWN, 100),
            Actions.WAIT
    };

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        int random = ThreadLocalRandom.current().nextInt(0, actions.length);
        return actions[random];
    }
}

package com.ericc.the.game.agencies;

import com.ericc.the.game.Direction;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalk implements Agency {

    static private final Action[] actions = {Actions.MOVE(Direction.UP), Actions.MOVE(Direction.RIGHT),
            Actions.MOVE(Direction.LEFT), Actions.MOVE(Direction.DOWN), Actions.WAIT};

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        int random = ThreadLocalRandom.current().nextInt(0, 5);
        return actions[random];
    }
}

package com.ericc.the.game.agencies;

import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalk implements Agency {

    static private final Action[] actions = {Actions.MOVE_DOWN, Actions.MOVE_LEFT,
            Actions.MOVE_RIGHT, Actions.MOVE_UP, Actions.NOTHING};

    @Override
    public Action chooseAction(PositionComponent pos) {
        int random = ThreadLocalRandom.current().nextInt(0, 5);
        return actions[random];
    }
}

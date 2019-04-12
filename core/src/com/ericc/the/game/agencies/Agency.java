package com.ericc.the.game.agencies;

import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

public interface Agency {
    Action chooseAction(PositionComponent pos, StatsComponent stats);
}

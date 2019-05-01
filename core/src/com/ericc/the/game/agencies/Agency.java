package com.ericc.the.game.agencies;

import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

public interface Agency {
    // This method can return null, to signal that the agency is awaiting user input.
    Action chooseAction(PositionComponent pos, StatsComponent stats);
}

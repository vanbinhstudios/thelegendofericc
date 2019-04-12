package com.ericc.the.game.agencies;

import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.PositionComponent;

public interface Agency {
    Action chooseAction(PositionComponent pos);
}

package com.ericc.the.game.agencies;

import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;

public class ProjectileAgency implements Agency {
    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        return Actions.FLY(10);
    }
}
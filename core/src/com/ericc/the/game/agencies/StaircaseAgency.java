package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.map.StaircaseDestination;

public class StaircaseAgency implements Agency {
    public PositionComponent pos;
    public StaircaseDestination dest;
    private Dungeon dungeon;

    public StaircaseAgency(PositionComponent pos, StaircaseDestination destination, Dungeon dungeon) {
        this.pos = pos;
        this.dest = destination;
        this.dungeon = dungeon;
    }

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        Entity standing = pos.map.entityMap.get(pos.xy);
        if (standing != null && Mappers.player.has(standing)) {
            return new TeleportAction(dest, dungeon);
        } else {
            return Actions.NOTHING;
        }
    }
}

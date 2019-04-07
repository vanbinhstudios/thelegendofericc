package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.StaircaseAgency;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.map.StaircaseDestination;
import com.ericc.the.game.utils.GridPoint;

public class Stairs extends Entity {
    public PositionComponent pos;

    public Stairs(GridPoint xy, Map map, StaircaseDestination destination) {
        pos = new PositionComponent(xy, map);
        add(pos);
        add(new RenderableComponent(
                destination == StaircaseDestination.DESCENDING ? Models.stairsDown : Models.stairsUp,
                1));
        add(new AgencyComponent(new StaircaseAgency(pos, destination)));
        add(new FixedInitiativeComponent(100));
        // TODO Stairs should be impassable for entities other than player
    }
}

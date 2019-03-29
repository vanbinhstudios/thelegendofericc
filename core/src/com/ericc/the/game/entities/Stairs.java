package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.agencies.StaircaseAgency;
import com.ericc.the.game.components.AgencyComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.map.StaircaseDestination;

public class Stairs extends Entity {
    public PositionComponent pos;

    public Stairs(int x, int y, Map map, StaircaseDestination destination) {
        pos = new PositionComponent(x, y, map);
        add(pos);
        add(new RenderableComponent(destination == StaircaseDestination.DESCENDING ? Models.stairsDown : Models.stairsUp));
        add(new AgencyComponent(new StaircaseAgency(pos, destination)));
    }

    public Stairs(GridPoint2 pos, Map map, StaircaseDestination destination) {
        this(pos.x, pos.y, map, destination);
    }
}

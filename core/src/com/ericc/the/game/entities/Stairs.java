package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.map.StaircaseDestination;

public class Stairs extends Entity {
    public Stairs(int x, int y, Map map, StaircaseDestination destination) {
        add(new PositionComponent(x, y, map));
        add(new RenderableComponent(destination == StaircaseDestination.DESCENDING ? Models.stairsDown : Models.stairsUp));
        add(new CurrentActionComponent(Actions.NOTHING));
        add(new InteractivityComponent());
        add(new OneSidedComponent());
        add(new StaircaseDestinationComponent(destination));
    }

    public Stairs(GridPoint2 pos, Map map, StaircaseDestination destination) {
        this(pos.x, pos.y, map, destination);
    }
}

package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SafetyMapComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.Area;
import com.ericc.the.game.utils.GridPoint;

import java.util.ArrayList;

public class AstarAgency implements Agency {
    public static final int RADIUS = 5;
    public int moveDelay = 33;
    private RandomWalk randomWalk = new RandomWalk();
    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        Map map = pos.map;
        for (int x = pos.xy.x - RADIUS; x < pos.xy.x + RADIUS; ++x) {
            for (int y = pos.xy.y - RADIUS; y < pos.xy.y + RADIUS; ++y) {
                Entity target = map.collisionMap.get(new GridPoint(x, y));
                if (target == null)
                    continue;

                if (Mappers.player.has(target)) {
                    if (stats.health < 0.5 * stats.maxHealth) {
                        SafetyMapComponent sm = Mappers.safety.get(target);
                        if (sm.distance == null)
                            return Actions.WAIT;
                        int min = 1000000;
                        GridPoint minMove = null;
                        for (GridPoint move : Moves.moves) {
                            GridPoint point = pos.xy.add(move);
                            if (pos.map.isPassable(point) && sm.distance[point.x][point.y] < min) {
                                min = sm.distance[point.x][point.y];
                                minMove = move;
                            }
                        }
                        if (minMove == null)
                            return Actions.WAIT;
                        else
                            return new MovementAction(Direction.fromGridPoint(minMove), moveDelay, MovementAction.MovementType.RUN);
                    } else {
                        ArrayList<Direction> path = pos.map.makePath(pos.xy, new GridPoint(x, y));
                        if (path == null) {
                            return Actions.WAIT;
                        }
                        Direction dir = path.get(0);
                        GridPoint targetPos = pos.xy.add(GridPoint.fromDirection(dir));
                        Entity obstacle = map.getEntity(targetPos);
                        if (obstacle != null && Mappers.player.has(obstacle)) {
                            return Actions.AOE_ATTACK(Models.sword, Area.square(targetPos, 0), dir, 100, 5);
                        }
                        return new MovementAction(path.get(0), moveDelay, MovementAction.MovementType.RUN);
                    }
                }
            }
        }
        return randomWalk.chooseAction(pos, stats);
    }
}

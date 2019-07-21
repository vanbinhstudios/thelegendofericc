package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SafetyMapComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.helpers.Charge;
import com.ericc.the.game.helpers.Moves;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class CowAgency implements Agency {
    public static final int RADIUS = 5;
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
                            return new MovementAction(Direction.fromGridPoint(minMove), 2000 / stats.agility, MovementAction.MovementType.WALK);
                    }
                }
            }
        }

        if (stats.health < 0.7 * stats.maxHealth) {
            return Actions.SELFHEAL();
        }

        return randomWalk.chooseAction(pos, stats);
    }
}

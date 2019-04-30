package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.Area;
import com.ericc.the.game.utils.GridPoint;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class AstarAgency implements Agency {
    public static final int RADIUS = 4;
    public int moveDelay = 25;
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
                        int dx = x - pos.xy.x;
                        int dy = y - pos.xy.y;
                        if (abs(dy) > abs(dx)) {
                            if (dy > 0) {
                                return new MovementAction(Direction.DOWN, moveDelay, MovementAction.MovementType.RUN);
                            } else {
                                return new MovementAction(Direction.UP, moveDelay, MovementAction.MovementType.RUN);
                            }
                        } else {
                            if (dx > 0) {
                                return new MovementAction(Direction.LEFT, moveDelay, MovementAction.MovementType.RUN);
                            } else {
                                return new MovementAction(Direction.RIGHT, moveDelay, MovementAction.MovementType.RUN);
                            }
                        }
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

package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.*;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BossAgency implements Agency {
    public static final int RADIUS = 6;
    public int moveDelay = 200;
    private RandomWalk randomWalk = new RandomWalk();
    private int state = 0;
    private int focus = 0;
    private Direction chargeDir = Direction.DOWN;

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        while (true) {
            if (state == 0) {
                Action choice = state0(pos, stats);
                if (choice != null)
                    return choice;
            }
            if (state == 1) {
                Action choice = state1(pos, stats);
                if (choice != null)
                    return choice;
            }
            if (state == 2) {
                Action choice = state2(pos, stats);
                if (choice != null)
                    return choice;
            }
        }
    }

    private Action state0(PositionComponent pos, StatsComponent stats) {
        Map map = pos.map;
        for (int x = pos.xy.x - RADIUS; x < pos.xy.x + RADIUS; ++x) {
            for (int y = pos.xy.y - RADIUS; y < pos.xy.y + RADIUS; ++y) {
                Entity target = map.collisionMap.get(new GridPoint(x, y));
                if (target == null)
                    continue;

                if (Mappers.player.has(target)) {
                    if (Math.random() < 0.2) {
                        state = 1;
                        return null;
                    }
                    ArrayList<Direction> path = pos.map.makePath(pos.xy, new GridPoint(x, y));
                    if (path == null) {
                        return Actions.WAIT;
                    }
                    Direction dir = path.get(0);
                    GridPoint targetPos = pos.xy.add(GridPoint.fromDirection(dir));
                    Entity obstacle = map.getEntity(targetPos);
                    if (obstacle != null && Mappers.player.has(obstacle)) {
                        return Actions.AOE_ATTACK(Models.sword, pos.map.calculateFOV(targetPos, 0), dir, 100, 100, 0);
                    }
                    return new MovementAction(path.get(0), moveDelay, MovementAction.MovementType.RUN);
                }
            }
        }
        return randomWalk.chooseAction(pos, stats);
    }

    private Action state1(PositionComponent pos, StatsComponent stats) {
        Map map = pos.map;
        for (int x = pos.xy.x - RADIUS; x < pos.xy.x + RADIUS; ++x) {
            for (int y = pos.xy.y - RADIUS; y < pos.xy.y + RADIUS; ++y) {
                Entity target = map.collisionMap.get(new GridPoint(x, y));
                if (target == null)
                    continue;

                if (Mappers.player.has(target)) {
                    if (Math.abs(x - pos.xy.x) + Math.abs(y - pos.xy.y) == 1) {
                        state = 2;
                        return null;
                    }
                    ArrayList<Direction> path = pos.map.makePath(pos.xy, new GridPoint(x, y));
                    if (path == null) {
                        return Actions.WAIT;
                    }
                    return new MovementAction(path.get(0), 40, MovementAction.MovementType.RUN);
                }
            }
        }
        state = 0;
        return randomWalk.chooseAction(pos, stats);
    }

    private Action state2(PositionComponent pos, StatsComponent stats) {
        if (stats.focus >= 100) {
            stats.focus = 0;
            state = 0;
            return Actions.AOE_ATTACK(Models.storm, pos.map.calculateFOV(pos.xy, 100).stream().filter(e->(e.x + e.y - pos.xy.x + pos.xy.y)%2==1).collect(Collectors.toList()), pos.dir, 100, 40, 0);
        }
        stats.focus += 2;
        return new ChargeAction(10);
    }
}

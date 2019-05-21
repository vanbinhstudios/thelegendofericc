package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Direction;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class ArcherAgency implements Agency {
    public static final int RADIUS = 5;
    private RandomWalk randomWalk = new RandomWalk();

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        Map map = pos.map;
        for (int x = pos.xy.x - RADIUS; x < pos.xy.x + RADIUS; ++x) {
            Entity target = map.collisionMap.get(new GridPoint(x, pos.xy.y));
            if (target == null) continue;

            if (Mappers.player.has(target)) {
                return Actions.SHOOT(pos.xy.x > x ? Direction.LEFT : Direction.RIGHT, 100, stats.strength); // TODO change model here
            }
        }

        for (int y = pos.xy.y - RADIUS; y < pos.xy.y + RADIUS; ++y) {
            Entity target = map.collisionMap.get(new GridPoint(pos.xy.x, y));
            if (target == null) continue;

            if (Mappers.player.has(target)) {
                return Actions.SHOOT(pos.xy.y > y ? Direction.DOWN : Direction.UP, 100, stats.strength); // TODO same here
            }
        }

        return randomWalk.chooseAction(pos, stats);
    }
}

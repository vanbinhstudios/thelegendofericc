package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.helpers.Charge;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class ChargeToPlayerAgency implements Agency {
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
                    return Charge.returnAction(pos, x, y, map, moveDelay, stats);
                }
            }
        }
        return randomWalk.chooseAction(pos, stats);
    }
}

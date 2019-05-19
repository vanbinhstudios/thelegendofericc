package com.ericc.the.game.agencies;

import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Models;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.DieAction;
import com.ericc.the.game.actions.WaitAction;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.Area;
import com.ericc.the.game.utils.GridPoint;

import java.util.ArrayList;
import java.util.Random;

public class StormAgency implements Agency {
    public static final int RADIUS = 5;
    private static Random rng = new Random();

    private int duration = 1000;

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        if (duration <= 0) {
            return new DieAction();
        }
        ArrayList<GridPoint> targets = new ArrayList<>();
        Map map = pos.map;
        for (int x = pos.xy.x - RADIUS; x < pos.xy.x + RADIUS; ++x) {
            for (int y = pos.xy.y - RADIUS; y < pos.xy.y + RADIUS; ++y) {
                GridPoint current = new GridPoint(x, y);
                Entity target = map.collisionMap.get(current);
                if (target != null && Mappers.hostile.has(target))
                    targets.add(current);
            }
        }

        duration -= 20;

        if (targets.isEmpty()) {
            return new WaitAction(20);
        }

        GridPoint randomTarget = targets.get(rng.nextInt(targets.size()));
        return Actions.AOE_ATTACK(Models.sword, Area.square(randomTarget, 0), 200, 60);
    }
}

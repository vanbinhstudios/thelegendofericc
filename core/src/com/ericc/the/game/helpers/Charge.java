package com.ericc.the.game.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
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

public class Charge {
    public static Action returnAction(PositionComponent pos, int x, int y, Map map, int moveDelay, StatsComponent stats) {
        ArrayList<Direction> path = pos.map.makePath(pos.xy, new GridPoint(x, y));
        if (path == null) {
            return Actions.WAIT;
        }
        Direction dir = path.get(0);
        GridPoint targetPos = pos.xy.add(GridPoint.fromDirection(dir));
        Entity obstacle = map.getEntity(targetPos);
        if (obstacle != null && Mappers.player.has(obstacle)) {
            if (stats.canUseSkills && stats.mana > 50) {
                int rand = MathUtils.random(0, 3);

                switch (rand) {
                    case 1:
                        return Actions.AOE_ATTACK(Models.explosion3, Area.ray(pos.xy, pos.dir, 8), pos.dir, 300, stats.strength, 50); // TODO change models
                    default:
                    case 0:
                        return Actions.AOE_ATTACK(Models.explosion1, Area.square(pos.xy, 2), pos.dir, 200, stats.strength, 50); // TODO here too
                }
            } else {
                return Actions.AOE_ATTACK(Models.sword, Area.square(targetPos, 0), dir, 100, stats.strength, 50);
            }
        }
        return new MovementAction(path.get(0), moveDelay, MovementAction.MovementType.RUN);
    }
}

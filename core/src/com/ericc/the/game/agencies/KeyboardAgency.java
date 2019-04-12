package com.ericc.the.game.agencies;

import com.ericc.the.game.*;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.utils.GridPoint;

public class KeyboardAgency implements Agency {
    private KeyboardController controller;

    public KeyboardAgency(KeyboardController controller) {
        this.controller = controller;
    }

    private boolean checkIfCanMove(Map map, GridPoint xy) {
        return map.isPassable(xy.x, xy.y) || !map.isFloor(xy.x, xy.y);
    }

    private boolean checkIfCanAttack(Map map, GridPoint xy) {
        return Mappers.hostile.has(map.getEntity(xy));
    }

    private boolean checkIfCanPush(Map map, GridPoint xy) {
        return Mappers.collision.has(map.getEntity(xy)) && Mappers.collision.get(map.getEntity(xy)).isPushable;
    }

    private Action handleDirectionalInput(PositionComponent pos, Direction direction) {
        GridPoint targetXY = pos.xy.add(GridPoint.fromDirection(direction));
        if (checkIfCanMove(pos.map, targetXY)) {
            if (VeryUglyGlobalState.playerRunning) {
                return Actions.RUN(direction, 50);
            } else {
                return Actions.MOVE(direction, 100);
            }
        } else if (checkIfCanPush(pos.map, targetXY)) {
            System.out.println("pushable");
            return Actions.PUSH(direction);
        } else if (checkIfCanAttack(pos.map, targetXY)) {
            return Actions.DIRECTED_AOE_ATTACK(Models.sword, direction, 1, 1, 100, 40);
        } else {
            return SyncComponent.SYNC;
        }
    }

    @Override
    public Action chooseAction(PositionComponent pos, StatsComponent stats) {
        if (controller.right) {
            controller.right = false;
            return handleDirectionalInput(pos, Direction.RIGHT);
        } else if (controller.left) {
            controller.left = false;
            return handleDirectionalInput(pos, Direction.LEFT);
        } else if (controller.down) {
            controller.down = false;
            return handleDirectionalInput(pos, Direction.DOWN);
        } else if (controller.up) {
            controller.up = false;
            return handleDirectionalInput(pos, Direction.UP);
        } else if (controller.space) {
            controller.space = false;
            return Actions.LONG_WAIT;
        } else if (controller.q) {
            controller.q = false;
            return Actions.AOE_ATTACK(new GridPoint(-1, -1), Models.sword, Direction.UP, 3, 3, 300, 20);
        } else if (controller.e) {
            controller.e = false;
            return Actions.DIRECTED_AOE_ATTACK(Models.sword, pos.direction, 6, 1, 100, 40);
        } else if (controller.n) {
            controller.n = false;
            stats.delayMultiplier *= 2;
            return Actions.WAIT;
        } else if (controller.m) {
            controller.m = false;
            stats.delayMultiplier /= 2;
            return Actions.WAIT;
        } else {
            return SyncComponent.SYNC;
        }
    }
}

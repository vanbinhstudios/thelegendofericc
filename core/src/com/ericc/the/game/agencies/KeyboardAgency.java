package com.ericc.the.game.agencies;

import com.ericc.the.game.KeyboardController;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.components.PositionComponent;

public class KeyboardAgency implements Agency {
    private KeyboardController controller;

    public KeyboardAgency(KeyboardController controller) {
        this.controller = controller;
    }

    @Override
    public Action chooseAction(PositionComponent pos) {
        if (controller.right) {
            controller.right = false;
            if (pos.map.isPassable(pos.x + 1, pos.y) || !pos.map.isFloor(pos.x + 1, pos.y)) {
                return Actions.MOVE_RIGHT;
            } else {
                return Actions.ATTACK_RIGHT;
            }
        } else if (controller.left) {
            controller.left = false;
            if (pos.map.isPassable(pos.x - 1, pos.y) || !pos.map.isFloor(pos.x - 1, pos.y)) {
                return Actions.MOVE_LEFT;
            } else {
                return Actions.ATTACK_LEFT;
            }
        } else if (controller.down) {
            controller.down = false;
            if (pos.map.isPassable(pos.x, pos.y - 1) || !pos.map.isFloor(pos.x, pos.y - 1)) {
                return Actions.MOVE_DOWN;
            } else {
                return Actions.ATTACK_DOWN;
            }
        } else if (controller.up) {
            controller.up = false;
            if (pos.map.isPassable(pos.x, pos.y + 1) || !pos.map.isFloor(pos.x, pos.y + 1)) {
                return Actions.MOVE_UP;
            } else {
                return Actions.ATTACK_UP;
            }
        } else {
            return Actions.NOTHING;
        }
    }
}

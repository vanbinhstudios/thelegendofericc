package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.entities.Player;

public class KeyboardController extends InputAdapter {

    private Engine logicEngine;
    private Player player;

    public KeyboardController(Engine logicEngine, Player player) {
        this.logicEngine = logicEngine;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean action = true;
        switch (keycode) {
            case Input.Keys.S:
            case Input.Keys.DOWN:
                player.currentAction.action = Actions.MOVE_DOWN;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                player.currentAction.action = Actions.MOVE_UP;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                player.currentAction.action = Actions.MOVE_LEFT;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                player.currentAction.action = Actions.MOVE_RIGHT;
                break;
            case Input.Keys.SPACE:
                player.currentAction.action = Actions.NOTHING;
                break;
            default:
                player.currentAction.action = Actions.NOTHING;
                action = false;
                break;
        }
        if (action) {
            logicEngine.update(1);
        }
        return true;
    }
}

package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.helpers.CameraZoom;

public class KeyboardController extends InputAdapter {

    private Engine logicEngine;
    private Player player;
    private CameraZoom zoom;

    public KeyboardController(Engine logicEngine, Player player, OrthographicCamera camera) {
        this.logicEngine = logicEngine;
        this.player = player;
        this.zoom = new CameraZoom(camera);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (playersGUIActions(keycode)) {
            return false;
        }

        switch (keycode) {
            case Input.Keys.S:
            case Input.Keys.DOWN:
                player.intention.currentIntent = Actions.MOVE_DOWN;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                player.intention.currentIntent = Actions.MOVE_UP;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                player.intention.currentIntent = Actions.MOVE_LEFT;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                player.intention.currentIntent = Actions.MOVE_RIGHT;
                break;
            case Input.Keys.SPACE:
            default:
                player.intention.currentIntent = Actions.NOTHING;
                break;
        }

        logicEngine.update(1);
        return false;
    }

    /**
     * Actions that player takes and should not affect the turn counter.
     * @return true if the action that should be taken should not update the turn counter
     */
    private boolean playersGUIActions(int keycode) {
        switch (keycode) {
            case Input.Keys.MINUS:
                zoom.zoomOutCamera();
                break;
            case Input.Keys.PLUS:
                zoom.zoomInCamera();
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Reacts to scroll on mouse and changes the zoom of a map.
     */
    @Override
    public boolean scrolled(int amount) {
        zoom.zoomAnyCamera(amount);
        return false;
    }
}

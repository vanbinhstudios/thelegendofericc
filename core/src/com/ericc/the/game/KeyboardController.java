package com.ericc.the.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.entities.Player;

public class KeyboardController extends InputAdapter {

    private Engine logicEngine;
    private Player player;
    private OrthographicCamera camera;
    private float initialCameraZoom;
    private final float cameraZoomMaxDeviation = .3f;
    private final float cameraChange = .05f;

    public KeyboardController(Engine logicEngine, Player player, OrthographicCamera camera) {
        this.logicEngine = logicEngine;
        this.player = player;
        this.camera = camera;
        this.initialCameraZoom = this.camera.zoom;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (playersGUIActions(keycode)) {
            return false;
        }

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
                zoomCamera(this.cameraChange);
                break;
            case Input.Keys.PLUS:
                zoomCamera((-1) * this.cameraChange);
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Zooms the camera by the given value (either out or in)
     * @param zoom the value to add to a camera zoom
     */
    private void zoomCamera(float zoom) {
        if (canZoom(camera.zoom + zoom)) {
            this.camera.zoom += zoom;
        }
    }

    /**
     * Checks whether we can zoom in or zoom out
     * @param zoom camera zoom altogether (sum of the previous zoom and the adder)
     * @return true if zoom is possible, false otherwise
     */
    private boolean canZoom(float zoom) {
        if (MainGame.DEBUG) {
            return true;
        }

        return zoom >= (this.initialCameraZoom - this.cameraZoomMaxDeviation)
                && zoom <= (this.initialCameraZoom + this.cameraZoomMaxDeviation);
    }

    /**
     * Reacts to scroll on mouse and changes the zoom of a map.
     */
    @Override
    public boolean scrolled(int amount) {
        zoomCamera(this.cameraChange * amount);
        return false;
    }
}

package com.ericc.the.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.helpers.CameraZoom;

public class KeyboardController extends InputAdapter {

    private GameEngine engines;
    private CameraZoom zoom;
    public boolean up, down, left, right, space;

    public void clean() {
        up = down = left = right = space = false;
    }

    public KeyboardController(GameEngine engines, OrthographicCamera camera) {
        this.engines = engines;
        this.zoom = new CameraZoom(camera);
        clean();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (playersGUIActions(keycode)) {
            return false;
        }

        switch (keycode) {
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = true;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.SPACE:
                space = true;
                break;
            default:
                break;
        }

        engines.updateLogicEngine();
        return false;
    }

    /**
     * Actions that player takes and should not affect the turn counter.
     *
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
            case Input.Keys.R:
                VeryUglyGlobalState.playerRunning = !VeryUglyGlobalState.playerRunning;
                break;
            /*
            case Input.Keys.O:
                if (DEBUG) {
                    player.pos.map.makeFogCoverTheEntireMap();
                }
                break;
            case Input.Keys.I:
                if (DEBUG) {
                    FieldOfViewComponent playersFov = Mappers.fov.get(player);
                    playersFov.visibility.setAll();
                    engines.disableFieldOfViewSystem();
                }
                break;
            case Input.Keys.U:
                if (DEBUG) {
                    engines.enableFieldOfViewSystem();
                }
                break;
            */
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

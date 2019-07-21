package com.ericc.the.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ericc.the.game.helpers.CameraZoom;

public class KeyboardController extends InputAdapter {

    public boolean up, down, left, right, space, q, e, n, m, f, r, right_bracket, i;
    public float up_delay, down_delay, left_delay, right_delay;
    public boolean up_pressed, down_pressed, left_pressed, right_pressed;
    private GameEngine engines;
    private CameraZoom zoom;

    public KeyboardController(GameEngine engines, OrthographicCamera camera) {
        this.engines = engines;
        this.zoom = new CameraZoom(camera);
        clean();
    }

    public void clean() {
        f = n = m = e = q = up = down = left = right = space = false;
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
                down_pressed = true;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up = true;
                up_pressed = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = true;
                left_pressed = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = true;
                right_pressed = true;
                break;
            case Input.Keys.SPACE:
                space = true;
                break;
            case Input.Keys.Q:
                q = true;
                break;
            case Input.Keys.E:
                e = true;
                break;
            case Input.Keys.N:
                n = true;
                break;
            case Input.Keys.M:
                m = true;
                break;
            case Input.Keys.F:
                f = true;
                break;
            case Input.Keys.R:
                r = true;
                break;
            case Input.Keys.RIGHT_BRACKET:
                right_bracket = true;
                break;
            case Input.Keys.I:
                i = true;
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down_pressed = false;
                down_delay = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up_pressed = false;
                up_delay = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left_pressed = false;
                left_delay = 0;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right_pressed = false;
                right_delay = 0;
                break;
        }

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

    public void tick(float dt) {
        final float repeatRate = 0.16f;

        if (down_pressed)
            down_delay += dt;
        if (up_pressed)
            up_delay += dt;
        if (right_pressed)
            right_delay += dt;
        if (left_pressed)
            left_delay += dt;

        if (down_pressed && down_delay > repeatRate) {
            down_delay %= repeatRate;
            down = true;
        }

        if (left_pressed && left_delay > repeatRate) {
            left_delay %= repeatRate;
            left = true;
        }

        if (right_pressed && right_delay > repeatRate) {
            right_delay %= repeatRate;
            right = true;
        }

        if (up_pressed && up_delay > repeatRate) {
            up_delay %= repeatRate;
            up = true;
        }
    }
}

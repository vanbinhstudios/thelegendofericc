package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class KeyboardControls extends InputAdapter implements InputProcessor {
    public boolean up, down, left, right;

    // Debug mode flag enabled with BACKSPACE
    public boolean debug = false;

    public KeyboardControls() {
    }

    // Called on key press
    @Override
    public boolean keyDown(int keycode) {
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
        }
        return false;
    }

    // Called on key release
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = false;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.BACKSPACE:
                debug = !debug;
        }
        return false;
    }
}
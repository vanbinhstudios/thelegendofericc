package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class KeyboardControls extends InputAdapter implements InputProcessor {
    OrthographicCamera camera;

    public boolean up, down, left, right;

    public boolean LMB, RMB, click;

    public Vector2 click_pos = new Vector2();
    public Vector2 map_click_pos = new Vector2();

    public boolean debug = false;

    private int screen_width;
    private int screen_height;

    public KeyboardControls(int screen_width, int screen_height, OrthographicCamera camera) {
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        this.camera = camera;
    }

    private void setClickPos(int pos_x, int pos_y) {
        click_pos.set(pos_x, screen_height - pos_y);
        map_click_pos.set(getCoords(click_pos));
    }

    private Vector2 getCoords(Vector2 mouse) {
        Vector3 temp = new Vector3(mouse.x, screen_height - mouse.y, 0);
        this.camera.unproject(temp);
        return new Vector2(temp.x, temp.y);
    }

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

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0 && button == Input.Buttons.LEFT) {
            LMB = true;
        } else if (pointer == 0 && button == Input.Buttons.RIGHT) {
            RMB = true;
        }

        setClickPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setClickPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

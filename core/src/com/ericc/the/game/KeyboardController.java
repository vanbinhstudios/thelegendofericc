package com.ericc.the.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.helpers.CameraZoom;
import com.ericc.the.game.map.CurrentMap;
import com.ericc.the.game.map.Dungeon;

import static com.ericc.the.game.MainGame.DEBUG;

public class KeyboardController extends InputAdapter {

    private Engines engines;
    private Player player;
    private CameraZoom zoom;
    private Dungeon dungeon;

    public KeyboardController(Engines engines, Player player, OrthographicCamera camera, Dungeon dungeon) {
        this.engines = engines;
        this.player = player;
        this.zoom = new CameraZoom(camera);
        this.dungeon = dungeon;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (playersGUIActions(keycode)) {
            return false;
        }

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
            case Input.Keys.N: // like NEXT -> changes the map to the next one
                player.currentAction.action = Actions.NOTHING;
                if (DEBUG) {
                    CurrentMap.setMap(dungeon.goToNext(), engines);
                }
                break;
            case Input.Keys.P: // like PREVIOUS -> changes the map to the previous one
                player.currentAction.action = Actions.NOTHING;
                if (DEBUG) {
                    CurrentMap.setMap(dungeon.goToPrevious(), engines);
                }
                break;
            default:
                player.currentAction.action = Actions.NOTHING;
                action = false;
                break;
        }
        if (action) {
            engines.updateLogicEngine();
        }
        return true;
    }

    /**
     * Actions that player takes (in GUI) and should not affect the turn counter.
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

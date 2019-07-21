package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.ericc.the.game.ui.UiSkin;
import com.ericc.the.game.ui.screens.GameScreen;

public class Popup {

    public static void show(String title, String text) {
        Dialog dialog = new Dialog(title, UiSkin.get(), "dialog");
        dialog.text(text);
        dialog.button("OK", true);
        dialog.key(Input.Keys.ENTER, true);
        dialog.show(GameScreen.gameScreen.overlay.getStage());
    }
}

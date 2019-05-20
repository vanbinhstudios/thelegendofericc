package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ericc.the.game.MainGame;

public class Popup {

    public static void show(String text) {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Dialog dialog = new Dialog("Level up!", skin, "dialog");
        dialog.text(text);
        dialog.button("OK", true); //sends "true" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.show(MainGame.gamescreen.overlay.getStage());
    }


}

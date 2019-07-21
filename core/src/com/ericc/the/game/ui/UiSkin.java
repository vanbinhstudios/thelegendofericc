package com.ericc.the.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UiSkin {
    private static Skin INSTANCE;

    public static Skin get() {
        if (INSTANCE == null) {
            INSTANCE = new Skin(Gdx.files.internal("uiskin.json"));
        }
        return INSTANCE;
    }
}

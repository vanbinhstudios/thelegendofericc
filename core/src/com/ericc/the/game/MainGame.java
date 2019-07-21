package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.ericc.the.game.ui.screens.MainMenu;

public class MainGame extends Game {

    public final static boolean DEBUG = true; ///< turns the debug mode on and off
    public static MainGame game;

    @Override
    public void create() {
        Media.loadAssets();
        game = this;
        setScreen(new MainMenu());
    }
}

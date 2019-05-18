package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.ericc.the.game.ui.screens.GameScreen;

public class MainGame extends Game {

    public final static boolean DEBUG = true; ///< turns the debug mode on and off
    public static GameScreen gamescreen;

    @Override
    public void create() {
        gamescreen = new GameScreen(this);
        setScreen(gamescreen);
    }
}

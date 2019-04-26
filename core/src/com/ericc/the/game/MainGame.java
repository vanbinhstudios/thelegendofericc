package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ericc.the.game.user_interface.screens.GameScreen;

public class MainGame extends Game {

    public final static boolean DEBUG = true; ///< turns the debug mode on and off
    private Screen gamescreen;

    @Override
    public void create() {
        gamescreen = new GameScreen(this);
        setScreen(gamescreen);
    }
}

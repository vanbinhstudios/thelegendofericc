package com.ericc.the.game;

import com.badlogic.gdx.Game;
import com.ericc.the.game.ui.screens.GameScreen;
import com.ericc.the.game.ui.screens.MainMenu;

public class MainGame extends Game {

    public static MainGame game;
    public final static boolean DEBUG = true; ///< turns the debug mode on and off

    @Override
    public void create() {
        game = this;
        setScreen(new MainMenu());
    }
}

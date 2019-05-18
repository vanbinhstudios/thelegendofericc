package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ericc.the.game.MainGame;
import com.ericc.the.game.ui.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Flash {

    static Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    public static void show(String text) {
        Label flash = new Label(text, style);
        flash.setColor(1, 1, 1, 0);
        flash.setFontScale(7);
        flash.setAlignment(Align.center);
        flash.setPosition(GameScreen.viewportWidth / 2, GameScreen.viewportHeight / 2);
        MainGame.gamescreen.overlay.getStage().addActor(flash);
        flash.addAction(sequence(alpha(0.7f, 0.06f), alpha(0, 0.06f), removeActor()));

    }
}

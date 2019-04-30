package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class InfoText extends Label {

    public InfoText(int level, int hp) {
        super("LVL: " + level + " HP: " + hp, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

}

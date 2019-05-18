package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class InfoText extends Label {

    static LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    public InfoText(int level, int hp, int arrows) {
        super("LVL: " + level + " HP: " + hp + " Arrows: " + arrows, style);
    }

}

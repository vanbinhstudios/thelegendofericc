package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class TopBar extends ProgressBar {

    public TopBar(int width, int height, Color background, Color filled) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        getStyle().background = Box.getColoredDrawable(width, height, background);
        getStyle().knob = Box.getColoredDrawable(0, height, filled);
        getStyle().knobBefore = Box.getColoredDrawable(width, height, filled);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.25f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

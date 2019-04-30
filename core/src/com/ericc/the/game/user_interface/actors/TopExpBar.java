package com.ericc.the.game.user_interface.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TopExpBar extends ProgressBar {

    public static Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

    public TopExpBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        getStyle().background = getColoredDrawable(width, height, Color.GOLD);
        getStyle().knob = getColoredDrawable(0, height, Color.YELLOW);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.YELLOW);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(0.2f);

        setAnimateDuration(0.25f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

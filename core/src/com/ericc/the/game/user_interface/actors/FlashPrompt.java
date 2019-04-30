package com.ericc.the.game.user_interface.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FlashPrompt extends Image {

    public FlashPrompt(TextureRegion skin, int x, int y, float fadeTime) {
        super();

        TextureRegionDrawable drawable = new TextureRegionDrawable(skin);
        this.setDrawable(drawable);
        this.setAlign(1);
        this.setPosition(x, y);
    }
}

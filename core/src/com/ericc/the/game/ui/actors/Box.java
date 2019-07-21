package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.ericc.the.game.Media;
import com.ericc.the.game.ui.UiSkin;

public class Box extends Table {

    public Box(int width, int height, Color fill) {
        super(UiSkin.get());
        this.setSize(width, height);
        this.setBackground(getColoredDrawable(width, height, fill));
    }

    public static Drawable getColoredDrawable(int width, int height, Color color) {
        Sprite sprite = new Sprite(Media.white);
        sprite.setColor(color);
        sprite.setSize(width, height);
        return new SpriteDrawable(sprite);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

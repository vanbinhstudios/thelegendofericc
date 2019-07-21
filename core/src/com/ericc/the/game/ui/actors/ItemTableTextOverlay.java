package com.ericc.the.game.ui.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ItemTableTextOverlay extends Table {

    public Box content;

    public ItemTableTextOverlay(int width, int height, int borderWeight) {
        super();
        this.setSize(width, height);
        this.setBackground(Box.getColoredDrawable(width, height, Color.CLEAR));

        content = new Box(width - borderWeight * 2, height - borderWeight * 2, Color.CLEAR);
        this.add(content);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

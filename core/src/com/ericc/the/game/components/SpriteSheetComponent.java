package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.Direction;

/**
 * The primary graphical component. Subject to violent change.
 */
public class SpriteSheetComponent implements Component {
    public TextureRegion[] sheet;
    public Sprite sprite;
    public Affine2 transform;

    public SpriteSheetComponent(TextureRegion up, TextureRegion right, TextureRegion down, TextureRegion left) {
        this.sheet = new TextureRegion[4];
        this.sheet[Direction.UP.getValue()] = up;
        this.sheet[Direction.DOWN.getValue()] = down;
        this.sheet[Direction.LEFT.getValue()] = left;
        this.sheet[Direction.RIGHT.getValue()] = right;

        this.sprite = new Sprite(down);
        this.sprite.setSize(1, 1);
        this.sprite.setOrigin(0, 0);
        this.transform = new Affine2();
    }

    public SpriteSheetComponent(TextureRegion region) {
        this.sheet = new TextureRegion[1];
        this.sheet[0] = region;

        this.sprite = new Sprite(region);
        this.sprite.setSize(1, 1);
        this.sprite.setOrigin(0, 0);
        this.transform = new Affine2();
    }
}

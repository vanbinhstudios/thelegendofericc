package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.Direction;

public class SpriteSheetComponent implements Component {

    // This should be TextureRegion[]. We will get there when we come up with sane asset management.
    public Texture[] sheet;
    public Sprite sprite;
    public Affine2 transform;

    public SpriteSheetComponent(Texture up, Texture right, Texture down, Texture left) {
        this.sheet = new Texture[4];
        this.sheet[Direction.UP.getValue()] = up;
        this.sheet[Direction.DOWN.getValue()] = down;
        this.sheet[Direction.LEFT.getValue()] = left;
        this.sheet[Direction.RIGHT.getValue()] = right;

        this.sprite = new Sprite(down);
        this.sprite.setSize(1, 1);
        this.sprite.setOrigin(0, 0);
        this.transform = new Affine2();
    }

    public SpriteSheetComponent(Texture texture) {
        this.sheet = new Texture[1];
        this.sheet[0] = texture;

        this.sprite = new Sprite(texture);
        this.sprite.setSize(1, 1);
        this.sprite.setOrigin(0, 0);
        this.transform = new Affine2();
    }
}

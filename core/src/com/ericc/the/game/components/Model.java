package com.ericc.the.game.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.ericc.the.game.Direction;

public class Model {
    public TextureRegion[] sheet;
    public Affine2 defaultTransform = new Affine2();
    public float width = 1.0f;
    public float height = 1.0f;

    public Model(TextureRegion up, TextureRegion right, TextureRegion down, TextureRegion left, Affine2 defaultTransform) {
        this.defaultTransform = defaultTransform;
        this.sheet = new TextureRegion[4];
        this.sheet[Direction.UP.getValue()] = up;
        this.sheet[Direction.DOWN.getValue()] = down;
        this.sheet[Direction.LEFT.getValue()] = left;
        this.sheet[Direction.RIGHT.getValue()] = right;
    }
}

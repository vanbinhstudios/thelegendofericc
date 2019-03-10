package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class Entity {
    private GridPoint2 pos;
    public Texture texture;
    private int width, height;

    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public Entity(GridPoint2 pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }
}

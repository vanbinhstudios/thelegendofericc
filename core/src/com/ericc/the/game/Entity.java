package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    public Vector2 pos;
    private Texture texture;
    private int width;
    private int height;

    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public Entity() {
        pos = new Vector2();
    }
}

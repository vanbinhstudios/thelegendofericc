package com.ericc.the.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class Entity {
    private GridPoint2 pos; ///< Indicates a position in a 2D grid (on a Map object, not in pixels - custom coordinates)
    public Texture texture;
    private int width, height; ///< Indicates the dimensions of a given Entity, usually width = height in our case

    /**
     * Draws a given Entity object on a given batch, used usually in any render function
     *
     * @param batch a batch to render this Entity on
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public Entity(GridPoint2 pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }
}

package com.ericc.the.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.ericc.the.game.Entity;
import com.ericc.the.game.Enums.TILE;

public class Tile extends Entity {
    public int size, col, row;
    public String code;
    public Texture texture;
    public TILE type;

    public Tile(float x, float y, int size, TILE type, Texture texture) {
        super();
        pos.x = x*size;
        pos.y = y*size;
        this.size = size;
        this.texture = texture;
        this.col = (int) x;
        this.row = (int) y;
        this.type = type;
        this.code = "";
    }

    private boolean isFloor() {
        return type == TILE.FLOOR;
    }

    private boolean isWall() {
        return type == TILE.WALL;
    }

    private boolean isVoid() {
        return type == TILE.VOID;
    }

    public boolean isPassable() {
        return !isWall() && !isVoid();
    }
}

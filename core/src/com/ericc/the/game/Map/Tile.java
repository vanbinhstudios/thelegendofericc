package com.ericc.the.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ericc.the.game.Enums.TILE;
import com.ericc.the.game.PositionComponent;
import com.ericc.the.game.RenderableComponent;

public class Tile {
    public RenderableComponent renderable;
    public PositionComponent pos;
    public int col, row;
    public String code;
    public TILE type;

    public Tile(int x, int y, TILE type, Texture texture) {
        pos = new PositionComponent(x, y);
        renderable = new RenderableComponent(new Sprite(texture));
        col = x;
        row = y;
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


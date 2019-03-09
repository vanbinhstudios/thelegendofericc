package com.ericc.the.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Map.Room;


public class Player extends Entity {
    public PositionComponent pos;
    public RenderableComponent renderable;

    public Player(Vector2 initialPos) {
        pos = new PositionComponent((int)initialPos.x, (int)initialPos.y);
        renderable = new RenderableComponent(new Sprite(Media.playerFront));
        renderable.sprite.setOrigin(0, -0.5f);
        add(pos);
        add(renderable);
    }

    public void update(KeyboardControls controls, Room room) {
        if (controls.left) {
            if (room.chunk.getTile(pos.y, pos.x - 1).isPassable()) {
                pos.x -= 1;
            }
            pos.dir = Direction.W;
        } else if (controls.right) {
            if (room.chunk.getTile(pos.y, pos.x + 1).isPassable()) {
                pos.x += 1;
            }
            pos.dir = Direction.E;
        } else if (controls.up) {
            if (room.chunk.getTile(pos.y + 1, pos.x).isPassable()) {
                pos.y += 1;
            }
            pos.dir = Direction.N;
        } else if (controls.down) {
            if (room.chunk.getTile(pos.y - 1, pos.x).isPassable()) {
                pos.y -= 1;
            }
            pos.dir = Direction.S;
        }
        controls.clear();
    }
}

package com.ericc.the.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ericc.the.game.Map.Room;


public class Player extends Entity {

    public Vector3 camPos;

    public Player(Vector2 initialPos) {
        super();
        type = Enums.ENTITY_TYPE.PLAYER;
        this.width = 16;
        this.height = 16;
        this.pos.x = initialPos.x;
        this.pos.y = initialPos.y;
        this.camPos = new Vector3();
        this.camPos.x = initialPos.x;
        this.camPos.y = initialPos.y;
        texture = Media.playerFront;
    }

    // TODO: Implement snap-to-grid movement for the character
    // TODO: Pack up the movement system into more convenient methods / classes to be reused for mobs
    // TODO: Fix the fugly if statements written far too late at night
    public void update(KeyboardControls controls, Room room) {
        if (controls.left) {
            texture = Media.playerLeft;
            if (room.chunk.getTile((int) pos.y / room.chunk.tileSize, (int) (pos.x) / room.chunk.tileSize).isPassable()) {
                this.pos.x -= 64 * Gdx.graphics.getDeltaTime();
                camPos.x = pos.x;
            }
            return;
        }

        if (controls.right) {
            texture = Media.playerRight;
            if (room.chunk.getTile((int) pos.y / room.chunk.tileSize, (int) (pos.x + 16) / room.chunk.tileSize).isPassable()) {
                this.pos.x += 64 * Gdx.graphics.getDeltaTime();
                camPos.x = pos.x;
            }
            return;
        }

        if (controls.up) {
            texture = Media.playerBack;
            if (room.chunk.getTile((int) (pos.y + 16) / room.chunk.tileSize, (int) pos.x / room.chunk.tileSize).isPassable()) {
                this.pos.y += 64 * Gdx.graphics.getDeltaTime();
                camPos.y = pos.y;
            }
            return;
        }

        if (controls.down) {
            texture = Media.playerFront;
            if (room.chunk.getTile((int) (pos.y) / room.chunk.tileSize, (int) pos.x / room.chunk.tileSize).isPassable()) {
                this.pos.y -= 64 * Gdx.graphics.getDeltaTime();
                camPos.y = pos.y;
            }
        }
    }
}

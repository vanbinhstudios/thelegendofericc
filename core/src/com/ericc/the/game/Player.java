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
        this.pos.x = initialPos.x;
        this.pos.y = initialPos.y;
        this.camPos = new Vector3();
        this.camPos.x = initialPos.x;
        this.camPos.y = initialPos.y;
        texture = Media.playerFront;
    }

    private final float speed = 5f;

    // TODO: Implement snap-to-grid movement for the character
    // TODO: Pack up the movement system into more convenient methods / classes to be reused for mobs
    // TODO: Fix the fugly if statements written far too late at night
    public void update(KeyboardControls controls, Room room) {
        if (controls.left) {
            texture = Media.playerLeft;
            if (room.chunk.getTile((int) pos.y, (int) (pos.x)).isPassable()) {
                this.pos.x -= speed * Gdx.graphics.getDeltaTime();
                camPos.x = pos.x;
            }
            return;
        }

        if (controls.right) {
            texture = Media.playerRight;
            if (room.chunk.getTile((int) pos.y, (int) (pos.x + 1)).isPassable()) {
                this.pos.x += speed * Gdx.graphics.getDeltaTime();
                camPos.x = pos.x;
            }
            return;
        }

        if (controls.up) {
            texture = Media.playerBack;
            if (room.chunk.getTile((int) (pos.y + 1), (int) pos.x).isPassable()) {
                this.pos.y += speed * Gdx.graphics.getDeltaTime();
                camPos.y = pos.y;
            }
            return;
        }

        if (controls.down) {
            texture = Media.playerFront;
            if (room.chunk.getTile((int) (pos.y), (int) pos.x).isPassable()) {
                this.pos.y -= speed * Gdx.graphics.getDeltaTime();
                camPos.y = pos.y;
            }
        }
    }
}

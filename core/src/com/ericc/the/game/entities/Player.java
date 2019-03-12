package com.ericc.the.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Direction;
import com.ericc.the.game.KeyboardControls;
import com.ericc.the.game.Media;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.AffineAnimationComponent;
import com.ericc.the.game.components.DirectionComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SpriteSheetComponent;
import com.ericc.the.game.map.Map;

public class Player extends Entity {

    public PositionComponent pos;
    public DirectionComponent dir;
    public SpriteSheetComponent renderable;

    public Player(int x, int y) {
        pos = new PositionComponent(x, y);
        dir = new DirectionComponent(Direction.DOWN);
        renderable = new SpriteSheetComponent(Media.playerBack, Media.playerRight, Media.playerFront, Media.playerLeft);
        renderable.sprite.setOrigin(0, -0.5f);
        add(pos);
        add(renderable);
        add(dir);
    }

    public Player(GridPoint2 pos) {
        this(pos.x, pos.y);
    }

    public void update(KeyboardControls controls, Map map) {
        int dx = 0;
        int dy = 0;
        if (controls.left) {
            if (map.isPassable(pos.x - 1, pos.y)) {
                dx = -1;
            }
            dir.direction = Direction.LEFT;
        } else if (controls.right) {
            if (map.isPassable(pos.x + 1, pos.y)) {
                dx = 1;
            }
            dir.direction = Direction.RIGHT;
        } else if (controls.up) {
            if (map.isPassable(pos.x, pos.y + 1)) {
                dy = 1;
            }
            dir.direction = Direction.UP;
        } else if (controls.down) {
            if (map.isPassable(pos.x, pos.y - 1)) {
                dy = -1;
            }
            dir.direction = Direction.DOWN;
        }
        if (dy != 0 || dx != 0) {
            add(new AffineAnimationComponent(new JumpAnimation(new Vector2(dx, dy), 0.6f, 0.15f)));
            pos.x += dx;
            pos.y += dy;
        }
        controls.clear();
    }
}

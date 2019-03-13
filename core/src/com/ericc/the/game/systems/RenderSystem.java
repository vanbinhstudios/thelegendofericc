package com.ericc.the.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SpriteSheetComponent;
import com.ericc.the.game.map.Map;

/**
 * The system responsible for drawing the map and the entities on the screen.
 * It is desirable to concentrate and contain graphics-related code here.
 */
public class RenderSystem extends EntitySystem {
    private Map map;
    private Camera camera;

    private SpriteBatch batch = new SpriteBatch();
    private ImmutableArray<Entity> entities; // Renderable entities.

    public RenderSystem(Map map, Camera camera) {
        super(9999); // Rendering should be the last system in effect.
        this.map = map;
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, SpriteSheetComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(.145f, .075f, .102f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();

        // Map rendering.
        // TODO: Optimization: don't draw tiles outside the view.
        for (int j = map.height() - 1; j >= 0; --j) {
            for (int i = 0; i < map.width(); ++i) {
                drawTile(i, j);
            }
        }

        // Entity rendering.
        // TODO: Optimization: don't draw entities outside the view.
        // TODO: Sort visible entities (sprites?) by y, to provide proper z-ordering.
        for (Entity entity : entities) {
            PositionComponent pos = Mappers.position.get(entity);
            SpriteSheetComponent render = Mappers.spriteSheet.get(entity);

            Affine2 transform = new Affine2();
            transform.translate(-render.sprite.getOriginX(), -render.sprite.getOriginY()); // From lower-corner space to origin space.
            transform.mul(render.transform); // Apply affine animations.
            transform.translate(new Vector2(pos.x, pos.y)); // Move to logical position.

            batch.draw(render.sprite, render.sprite.getWidth(), render.sprite.getWidth(), transform);
        }

        batch.end();
    }

    private void drawTile(int x, int y) {
        /*
        The nine-digit tile code describes the neighbourhood of the tile.
        1 - passable / floor
        0 - impassable / wall / void / out of map bounds

        100
        011 -> 001 011 100
        001
         */
        int code = encodeTile(x, y);

        /*
        This complicated algorithm attempts to paint convincing walls with a tileset representing (half-tile)-wide
        walls.
        Assumptions:
        - every texture used is square
        - corner and side-wall textures are half-width and are padded with "void" to the side on which they
        should appear on the tile: left wall is aligned to right side, etc.
        */

        // Floor tile.
        if ((code & 0b000010000) != 0) {
            batch.draw(Media.floors.get(map.getRandomNumber(x, y, TileTextureIndicator.FLOOR.getValue())),
                    x, y, 1, 1);
            return;
        }

        if ((code & 0b000000010) != 0) {
            // A down-facing wall.
            batch.draw(Media.wallDown.get(map.getRandomNumber(x, y, TileTextureIndicator.DOWN.getValue())),
                    x, y + 0.5f, 1, 1);
        } else {
            // Maybe down-facing corners?
            if ((code & 0b000100010) == 0 && (code & 0b000000100) != 0) {
                batch.draw(Media.wallRD, x, y + 0.5f, 0.5f, 1, 0, 1, 0.5f, 0);
            }
            if ((code & 0b000001010) == 0 && (code & 0b000000001) != 0) {
                batch.draw(Media.wallLD, x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 1, 1, 0);
            }
        }

        // Symmetrical (down-up) to the code above.
        if ((code & 0b010000000) != 0) {
            batch.draw(Media.wallUp.get(map.getRandomNumber(x, y, TileTextureIndicator.UP.getValue())),
                    x, y, 1, 1);
        } else {
            if ((code & 0b010100000) == 0 && (code & 0b100000000) != 0) {
                batch.draw(Media.wallRU, x, y, 0.5f, 1, 0, 1, 0.5f, 0);
            }
            if ((code & 0b010001000) == 0 && (code & 0b001000000) != 0) {
                batch.draw(Media.wallLU, x + 0.5f, y, 0.5f, 1, 0.5f, 1, 1, 0);
            }
        }

        if ((code & 0b010100010) == 0b000100000 || (code & 0b010101010) == 0b010100010) {
            // (A proper right-facing wall) || (the ending of a horizontal double-wall - special case for aesthetics).
            batch.draw(Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f, 0.5f, 1, 0, 0, 0.5f, 1);
        } else if ((code & 0b010100010) == 0b000100010) {
            // A right-and-up-facing wall. The up-facing part is already drawn. It looks better if the up-facing
            // part is drawn on top, so let's "draw the left part underneath". This meaning, we will only draw
            // the lower half of the left-facing wall.
            batch.draw(Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f, 0.5f, 0.5f, 0, 0.5f, 0.5f, 1);
        } else if ((code & 0b010100010) == 0b010100000) {
            // A right-and-down facing wall. See above.
            batch.draw(Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f + 0.5f, 0.5f, 0.5f, 0, 0, 0.5f, 0.5f);
        }

        // Symmetrical (right-left) to code above.
        if ((code & 0b010001010) == 0b000001000 || (code & 0b010101010) == 0b010001010) {
            batch.draw(Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 0, 1, 1);
        } else if ((code & 0b010001010) == 0b000001010) {
            batch.draw(Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 1, 1);
        } else if ((code & 0b010001010) == 0b010001000) {
            batch.draw(Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f + 0.5f, 0.5f, 0.5f, 0.5f, 0, 1, 0.5f);
        }
    }

    private int encodeTile(int x, int y) {
        int ans = 0;
        int cnt = 8;
        for (int j = y - 1; j <= y + 1; ++j) {
            for (int i = x - 1; i <= x + 1; ++i) {
                ans |= (map.isPassable(i, j) ? 1 : 0) << cnt;
                --cnt;
            }
        }
        return ans;
    }
}

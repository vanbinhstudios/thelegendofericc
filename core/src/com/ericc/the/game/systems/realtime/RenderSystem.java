package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.Media;
import com.ericc.the.game.TileTextureIndicator;
import com.ericc.the.game.components.*;
import com.ericc.the.game.map.Map;
import com.ericc.the.game.shaders.Shaders;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The system responsible for drawing the map and the entities on the screen.
 * It is desirable to concentrate and contain graphics-related code here.
 */
public class RenderSystem extends EntitySystem {
    private final Affine2 transformTmp = new Affine2();

    private SpriteBatch batch = new SpriteBatch();
    private ImmutableArray<Entity> entities; // Renderable entities.
    private ImmutableArray<Entity> viewers;

    private static Comparator<Entity> z_sort = Comparator
            .comparingInt((Entity e) -> Mappers.position.get(e).getY())
            .thenComparing((Entity e) -> Mappers.renderable.get(e).zOrder)
            .reversed();

    public RenderSystem(int priority) {
        super(priority); // Should be the last system to run.

        if (!Shaders.hsl.isCompiled())
            throw new GdxRuntimeException("Couldn't compile shader: " + Shaders.hsl.getLog());

        batch.setShader(Shaders.hsl);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RenderableComponent.class).get());
        viewers = engine.getEntitiesFor(Family.all(CameraComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        /*
        If we could access entities standing on a given position,
        there would be no need to perform the culling and sorting.
        If such caching is implemented at some point,
        rendering should be redone to take advantage of it.
         */

        for (Entity viewer : viewers) {
            // Not ready for multiple cameras.
            Gdx.gl.glClearColor(.09019f, .05882f, .08627f, 1); // Background color (hex: 170f16)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            CameraComponent cam = Mappers.camera.get(viewer);
            PositionComponent camPos = Mappers.position.get(viewer);

            batch.setProjectionMatrix(cam.viewport.getCamera().combined);
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            batch.begin();

            // Get a list of visible entities.
            ArrayList<Entity> visibleEntities = new ArrayList<>();
            final int margin = 5; // Assume that no sprite is more than 5 tiles away from it's logical position.
            for (Entity entity : entities) {
                PositionComponent pos = Mappers.position.get(entity);
                if (cam.left - margin <= pos.getX()
                        && pos.getX() <= cam.right + margin
                        && cam.bottom - margin <= pos.getY()
                        && pos.getY() <= cam.top + margin) {
                    visibleEntities.add(entity);
                }
            }

            // Depth-order the entities.
            visibleEntities.sort(z_sort);

            // Perform the drawing.
            int entityIndex = 0;
            for (int y = cam.top; y >= cam.bottom; --y) {
                for (int x = cam.left; x <= cam.right; ++x) {
                    if (camPos.map.hasBeenSeenByPlayer(x, y)) {
                        drawTile(batch, x, y, camPos.map);
                    }
                }
                while (entityIndex < visibleEntities.size()) {
                    PositionComponent pos = Mappers.position.get(visibleEntities.get(entityIndex));
                    if (pos.getY() < y)
                        break;
                    if (pos.map == camPos.map) {
                        drawEntity(visibleEntities.get(entityIndex));
                    }
                    ++entityIndex;
                }
            }
            while (entityIndex < visibleEntities.size()) {
                drawEntity(visibleEntities.get(entityIndex));
                ++entityIndex;
            }

            batch.end();
        }
    }

    private void drawEntity(Entity entity) {
        PositionComponent pos = Mappers.position.get(entity);
        RenderableComponent render = Mappers.renderable.get(entity);

        if (!render.visible && !(render.visibleInFog && pos.map.hasBeenSeenByPlayer(pos.xy)))
            return;

        transformTmp.idt();
        transformTmp.mul(render.model.defaultTransform); // From bottom-left-corner space to origin space.
        transformTmp.mul(render.transform); // Apply affine animations.
        transformTmp.translate(pos.getX(), pos.getY()); // Move to logical position.

        batch.setColor(0, render.saturation, render.brightness, render.alpha);
        batch.draw(render.region, render.model.width, render.model.height, transformTmp);

        if (Mappers.healthbar.has(entity)) {
            HealthbarComponent bar = Mappers.healthbar.get(entity);
            StatsComponent stats = Mappers.stats.get(entity);

            float barWidth = bar.model.width*((float)stats.health / (float)stats.maxHealth);

            transformTmp.idt();
            transformTmp.mul(bar.model.defaultTransform);
            transformTmp.mul(bar.transform);
            transformTmp.translate(pos.getX(), pos.getY());

            batch.draw(bar.region, barWidth, bar.model.height, transformTmp);
        }
    }

    private void drawTile(SpriteBatch batch, int x, int y, Map map) {

        batch.setColor(0.0f, map.saturation[x][y], map.brightness[x][y], 1.0f);
        boolean isStatic = !(map.brightness[x][y] > .7);

        /*
        The nine-digit tile code describes the neighbourhood of the tile.
        1 - passable / floor
        0 - impassable / wall / void / out of map bounds

        100
        011 -> 001 011 100
        001
         */
        int code = encodeTile(x, y, map);

        /*
        This complicated algorithm attempts to paint convincing walls with a tileset representing (half-tile)-wide
        walls.
        Assumptions:
        - every texture used is square
        - corner and side-wall textures are half-width and are padded with "void" to the side on which they
        should appear on the tile: left wall is aligned to right side, etc.
        */

        if (code == 0) {
            // Void.
            return;
        }

        if ((code & 0b000010000) != 0) {
            // Floor tile.
            batch.draw(Media.getRandomFloorTile(
                    x, y, map.getRandomNumber(x, y, TileTextureIndicator.FLOOR.getValue()), isStatic
            ), x, y, 1.001f, 1.001f);

            // Drawing decorations on the floor.
            int clutterType = map.getRandomClutter(x, y, TileTextureIndicator.FLOOR.getValue());

            if (clutterType < Media.clutter.size) {
                batch.draw(Media.clutter.get(clutterType),
                        x, y, 1.001f, 1.001f);
            }
            return;
        }

        if ((code & 0b000000010) != 0) {
            // A down-facing wall.
            batch.draw(Media.wallDown.get(map.getRandomNumber(x, y, TileTextureIndicator.DOWN.getValue())),
                    x, y + 0.5f, 1.001f, 1.001f);
        } else {
            // Maybe down-facing corners?
            if ((code & 0b000100010) == 0 && (code & 0b000000100) != 0) {
                draw(batch, Media.wallRD, x, y + 0.5f, 0.5f, 1, 0, 1, 0.5f, 0);
            }
            if ((code & 0b000001010) == 0 && (code & 0b000000001) != 0) {
                draw(batch, Media.wallLD, x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 1, 1, 0);
            }
        }

        // Symmetrical (down-up) to the code above.
        if ((code & 0b010000000) != 0) {
            batch.draw(Media.wallUp.get(map.getRandomNumber(x, y, TileTextureIndicator.UP.getValue())),
                    x, y, 1, 1);
        } else {
            if ((code & 0b010100000) == 0 && (code & 0b100000000) != 0) {
                draw(batch, Media.wallRU, x, y, 0.5f, 1, 0, 1, 0.5f, 0);
            }
            if ((code & 0b010001000) == 0 && (code & 0b001000000) != 0) {
                draw(batch, Media.wallLU, x + 0.5f, y, 0.5f, 1, 0.5f, 1, 1, 0);
            }
        }

        if ((code & 0b010100010) == 0b000100000 || (code & 0b010101010) == 0b010100010) {
            // (A proper right-facing wall) || (the ending of a horizontal double-wall - special case for aesthetics).
            draw(batch, Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f, 0.5f, 1, 0, 0, 0.5f, 1);
        } else if ((code & 0b010100010) == 0b000100010) {
            // A right-and-up-facing wall. The up-facing part is already drawn. It looks better if the up-facing
            // part is drawn on top, so let's "draw the left part underneath". This meaning, we will only draw
            // the lower half of the left-facing wall.
            draw(batch, Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f, 0.5f, 0.5f, 0, 0.5f, 0.5f, 1);
        } else if ((code & 0b010100010) == 0b010100000) {
            // A right-and-down facing wall. See above.
            draw(batch, Media.wallRight.get(map.getRandomNumber(x, y, TileTextureIndicator.RIGHT.getValue())),
                    x, y + 0.5f + 0.5f, 0.5f, 0.5f, 0, 0, 0.5f, 0.5f);
        }

        // Symmetrical (right-left) to code above.
        if ((code & 0b010001010) == 0b000001000 || (code & 0b010101010) == 0b010001010) {
            draw(batch, Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f, 0.5f, 1, 0.5f, 0, 1, 1);
        } else if ((code & 0b010001010) == 0b000001010) {
            draw(batch, Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 1, 1);
        } else if ((code & 0b010001010) == 0b010001000) {
            draw(batch, Media.wallLeft.get(map.getRandomNumber(x, y, TileTextureIndicator.LEFT.getValue())),
                    x + 0.5f, y + 0.5f + 0.5f, 0.5f, 0.5f, 0.5f, 0, 1, 0.5f);
        }

        // Drawing decorations on the upper walls.
        if ((code & 0b010000000) != 0) {
            int clutterType = map.getRandomClutter(x, y, TileTextureIndicator.UP.getValue());

            if (clutterType < Media.wallClutter.size) {
                batch.draw(Media.wallClutter.get(clutterType),
                        x, y, 1.001f, 1.001f);
            }
        }
    }

    private int encodeTile(int x, int y, Map map) {
        int ans = 0;
        int cnt = 8;
        for (int j = y - 1; j <= y + 1; ++j) {
            for (int i = x - 1; i <= x + 1; ++i) {
                ans |= (map.isFloor(i, j) ? 1 : 0) << cnt;
                --cnt;
            }
        }
        return ans;
    }

    /**
     * Draw a part of a texture region.
     * Similar to SpriteBatch.draw(texture, x, y, w, h, u, v, u2, v2), except
     * u, v, u2, v2 describe a fraction of the given region,
     * not a fraction of the entire texture.
     */
    private void draw(SpriteBatch batch, TextureRegion t,
                      float x, float y, float width, float height,
                      float u, float v, float u2, float v2) {
        u = t.getU2() * u + t.getU() * (1 - u);
        u2 = t.getU2() * u2 + t.getU() * (1 - u2);
        v = t.getV2() * v + t.getV() * (1 - v);
        v2 = t.getV2() * v2 + t.getV() * (1 - v2);
        batch.draw(t.getTexture(), x, y, width + 0.001f, height + 0.001f, u, v, u2, v2);
    }
}

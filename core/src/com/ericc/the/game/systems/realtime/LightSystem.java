package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.RenderableComponent;
import com.ericc.the.game.components.ScreenBoundariesComponent;
import com.ericc.the.game.entities.Screen;
import com.ericc.the.game.map.Map;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class LightSystem extends EntitySystem {
    private ImmutableArray<Entity> lightable; // Entities with affine animation currently attached.

    private FieldOfViewComponent fov;
    private Map map;
    private ScreenBoundariesComponent visibleMapArea;

    public LightSystem(FieldOfViewComponent fov, Map map, Screen screen) {
        this.fov = fov;
        this.map = map;
        this.visibleMapArea = Mappers.screenBoundaries.get(screen);
    }

    @Override
    public void addedToEngine(Engine engine) {
        lightable = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        // Update sprites according to orientation (face direction) of the Entity.
        for (Entity entity : lightable) {
            RenderableComponent render = Mappers.renderable.get(entity);
            PositionComponent pos = Mappers.position.get(entity);

            if (fov.visibility[pos.x][pos.y]) {
                render.lightLevel += (deltaTime) / 0.5f;
                render.lightLevel = min(render.lightLevel, 1f);
            } else {
                render.lightLevel -= (deltaTime) / 0.5f;
                render.lightLevel = max(render.lightLevel, 0f);
            }
        }

        for (int y = visibleMapArea.top + 5; y >= visibleMapArea.bottom - 5; --y) {
            for (int x = visibleMapArea.left - 5; x <= visibleMapArea.right + 5; ++x) {
                if (!map.inBoundaries(x, y)) continue;
                if (fov.visibility[x][y]) {
                    map.light[x][y] += (deltaTime) / 0.5f;
                    map.light[x][y] = min(map.light[x][y], 1f);
                } else {
                    map.light[x][y] -= (deltaTime) / 0.5f;
                    map.light[x][y] = max(map.light[x][y], 0f);
                }
            }
        }


    }
}

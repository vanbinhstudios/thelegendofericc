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
    private ImmutableArray<Entity> lightable;

    private FieldOfViewComponent fov;
    private Map map;
    private ScreenBoundariesComponent visibleArea;

    public LightSystem(FieldOfViewComponent fov, Map map, Screen screen) {
        this.fov = fov;
        this.map = map;
        this.visibleArea = Mappers.screenBoundaries.get(screen);
    }

    @Override
    public void addedToEngine(Engine engine) {
        lightable = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        final float fadingSpeed = 1 / 0.5f; // Full transition takes (1 / fadingSpeed) seconds.
        final int updateMargin = 5;

        for (Entity entity : lightable) {
            RenderableComponent render = Mappers.renderable.get(entity);
            PositionComponent pos = Mappers.position.get(entity);

            if (fov.visibility[pos.x][pos.y]) {
                render.lightLevel += deltaTime * fadingSpeed;
            } else {
                render.lightLevel -= deltaTime * fadingSpeed;
            }
            render.lightLevel = clamp(0, render.lightLevel, 1);
        }

        for (int y = visibleArea.top + updateMargin; y >= visibleArea.bottom - updateMargin; --y) {
            for (int x = visibleArea.left - updateMargin; x <= visibleArea.right + updateMargin; ++x) {
                if (!map.inBoundaries(x, y)) continue;

                if (fov.visibility[x][y]) {
                    map.light[x][y] += deltaTime * fadingSpeed;
                } else {
                    map.light[x][y] -= deltaTime * fadingSpeed;
                }
                map.light[x][y] = clamp(0, map.light[x][y], 1);
            }
        }
    }

    private float clamp(float lowerBound, float x, float upperBound) {
        x = min(x, upperBound);
        x = max(x, lowerBound);
        return x;
    }
}

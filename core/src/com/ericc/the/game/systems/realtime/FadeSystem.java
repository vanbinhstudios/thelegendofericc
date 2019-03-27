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
import com.ericc.the.game.map.CurrentMap;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class FadeSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private FieldOfViewComponent fov;
    private ScreenBoundariesComponent visibleArea;

    public FadeSystem(FieldOfViewComponent fov, Screen screen) {
        super(10000);

        this.fov = fov;
        this.visibleArea = Mappers.screenBoundaries.get(screen);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        final int updateMargin = 5;

        for (Entity entity : entities) {
            final float fadingSpeed = 1 / 0.4f; // Full transition takes (1 / fadingSpeed) seconds.
            RenderableComponent render = Mappers.renderable.get(entity);
            PositionComponent pos = Mappers.position.get(entity);

            float targetBrightness = fov.visibility.get(pos.x, pos.y) ? 1.0f : 0.3f;
            render.brightness = converge(render.brightness, targetBrightness, deltaTime * fadingSpeed);
            render.visible = render.brightness > 0.3f;
        }

        for (int y = visibleArea.top + updateMargin; y >= visibleArea.bottom - updateMargin; --y) {
            for (int x = visibleArea.left - updateMargin; x <= visibleArea.right + updateMargin; ++x) {
                final float fadingSpeed = 1 / 0.7f; // Full transition takes (1 / fadingSpeed) seconds.
                if (!CurrentMap.map.inBoundaries(x, y)) continue;
                if (!CurrentMap.map.hasBeenSeenByPlayer(x, y)) continue;

                float targetBrightness = fov.visibility.get(x, y) ? 1.0f : 0.5f;
                float targetSaturation = fov.visibility.get(x, y) ? 1.0f : 0.0f;
                CurrentMap.map.brightness[x][y] = converge(CurrentMap.map.brightness[x][y], targetBrightness, deltaTime * fadingSpeed);
                // Tune the minimum possible brightness up, because it looks weird when tiles are darker than the background.
                CurrentMap.map.brightness[x][y] = max(CurrentMap.map.brightness[x][y], 0.3f);
                CurrentMap.map.saturation[x][y] = converge(CurrentMap.map.saturation[x][y], targetSaturation, deltaTime * fadingSpeed);
            }
        }
    }

    private float converge(float current, float target, float delta) {
        if (current < target) {
            return min(current + delta, target);
        } else {
            return max(current - delta, target);
        }
    }
}

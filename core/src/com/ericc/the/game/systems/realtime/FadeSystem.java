package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class FadeSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> players;

    public FadeSystem() {
        super(10000);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, PositionComponent.class).get());
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, FieldOfViewComponent.class, CameraComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        final int updateMargin = 5;

        for (Entity entity : entities) {
            final float fadingSpeed = 1 / 0.4f; // Full transition takes (1 / fadingSpeed) seconds.
            RenderableComponent render = Mappers.renderable.get(entity);
            PositionComponent pos = Mappers.position.get(entity);

            float targetBrightness = 0.3f;

            for (Entity player : players) {
                FieldOfViewComponent fov = Mappers.fov.get(player);
                targetBrightness = max(targetBrightness, fov.visibility.get(pos.x, pos.y) ? 1.0f : 0.3f);
            }

            render.brightness = converge(render.brightness, targetBrightness, deltaTime * fadingSpeed);
            render.visible = render.brightness > 0.3f;
        }


        for (Entity player : players) {
            // Not ready for multiple cameras yet, because the implementation below is coupled to the
            // idea of screen boundaries too tightly.
            PositionComponent pos = Mappers.position.get(player);
            CameraComponent cam = Mappers.camera.get(player);
            FieldOfViewComponent fov = Mappers.fov.get(player);

            for (int y = cam.top + updateMargin; y >= cam.bottom - updateMargin; --y) {
                for (int x = cam.left - updateMargin; x <= cam.right + updateMargin; ++x) {
                    final float fadingSpeed = 1 / 0.7f; // Full transition takes (1 / fadingSpeed) seconds.
                    if (!pos.map.inBoundaries(x, y)) continue;
                    if (!pos.map.hasBeenSeenByPlayer(x, y)) continue;

                    float targetBrightness = fov.visibility.get(x, y) ? 1.0f : 0.5f;
                    float targetSaturation = fov.visibility.get(x, y) ? 1.0f : 0.0f;
                    pos.map.brightness[x][y] = converge(pos.map.brightness[x][y], targetBrightness, deltaTime * fadingSpeed);
                    // Tune the minimum possible brightness up, because it looks weird when tiles are darker than the background.
                    pos.map.brightness[x][y] = max(pos.map.brightness[x][y], 0.3f);
                    pos.map.saturation[x][y] = converge(pos.map.saturation[x][y], targetSaturation, deltaTime * fadingSpeed);
                }
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

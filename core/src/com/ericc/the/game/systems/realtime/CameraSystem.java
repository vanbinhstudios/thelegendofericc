package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.CameraComponent;
import com.ericc.the.game.components.PositionComponent;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * This system is responsible for updating the screen boundaries
 * in real time, it updates the Screen Entity, which holds
 * four variables - top bottom left right, which indicate
 * the position of a tile which is visible and is on the screen boundary.
 * <p>
 * DISCLAIMER:
 * The position of that tiles are given in a custom coordinates which
 * are explained in a Map, they are not in pixels.
 */
public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> viewers;

    public CameraSystem(int priority) {
        super(priority); // Should be updated before other realtime systems.
    }

    @Override
    public void addedToEngine(Engine engine) {
        viewers = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity viewer : viewers) {
            CameraComponent cam = Mappers.camera.get(viewer);
            PositionComponent pos = Mappers.position.get(viewer);

            Vector2 topLeft = cam.viewport.unproject(new Vector2(0, 0));
            cam.top = clamp(0, (int) topLeft.y, pos.map.height() - 1);
            cam.left = clamp(0, (int) topLeft.x, pos.map.width() - 1);

            Vector2 bottomRight = cam.viewport.unproject(new Vector2(cam.viewport.getScreenWidth(), cam.viewport.getScreenHeight()));
            cam.bottom = clamp(0, (int) bottomRight.y - 1, pos.map.height() - 1);
            cam.right = clamp(0, (int) bottomRight.x, pos.map.width() - 1);

            cam.viewport.getCamera().position.lerp(new Vector3(pos.xy.x, pos.xy.y, 0),
                    1 - (float) Math.pow(.1f, Gdx.graphics.getDeltaTime()));
            cam.viewport.getCamera().update();
        }
    }

    private int clamp(int lowerBound, int x, int upperBound) {
        x = min(x, upperBound);
        x = max(x, lowerBound);
        return x;
    }
}

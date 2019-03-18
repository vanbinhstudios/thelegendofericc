package com.ericc.the.game.systems.realtime;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.ScreenBoundariesComponent;
import com.ericc.the.game.entities.Screen;
import com.ericc.the.game.map.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class ScreenBoundariesGetterSystem extends EntitySystem {

    private Viewport viewport;
    private Map map;
    private Screen screen; // Renderable entities.

    public ScreenBoundariesGetterSystem(Viewport viewport, Map map, Screen screen) {
        super(1);

        this.viewport = viewport;
        this.map = map;
        this.screen = screen;
    }

    @Override
    public void addedToEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        ScreenBoundariesComponent boundaries = Mappers.screenBoundaries.get(screen);

        Vector2 topLeft = viewport.unproject(new Vector2(0, 0));
        boundaries.top = clamp(0, (int) topLeft.y, map.height() - 1);
        boundaries.left = clamp(0, (int) topLeft.x, map.width() - 1);

        Vector2 bottomRight = viewport.unproject(new Vector2(viewport.getScreenWidth(), viewport.getScreenHeight()));
        boundaries.bottom = clamp(0, (int) bottomRight.y - 1, map.height() - 1);
        boundaries.right = clamp(0, (int) bottomRight.x, map.width() - 1);
    }

    private int clamp(int lowerBound, int x, int upperBound) {
        x = min(x, upperBound);
        x = max(x, lowerBound);
        return x;
    }
}

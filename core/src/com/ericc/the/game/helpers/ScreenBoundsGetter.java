package com.ericc.the.game.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ericc.the.game.map.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class ScreenBoundsGetter {
    private Viewport viewport;
    private Map map;
    public int top, left, bottom, right;

    public ScreenBoundsGetter(Viewport viewport, Map map) {
        this.viewport = viewport;
        this.map = map;
    }

    public void update() {
        Vector2 topLeft = viewport.unproject(new Vector2(0, 0));
        top = clamp(0, (int) topLeft.y, map.height() - 1);
        left = clamp(0, (int) topLeft.x, map.width() - 1);

        Vector2 bottomRight = viewport.unproject(new Vector2(viewport.getScreenWidth(), viewport.getScreenHeight()));
        bottom = clamp(0, (int) bottomRight.y - 1, map.height() - 1);
        right = clamp(0, (int) bottomRight.x, map.width() - 1);
    }

    private int clamp(int lowerBound, int x, int upperBound) {
        x = min(x, upperBound);
        x = max(x, lowerBound);
        return x;
    }
}

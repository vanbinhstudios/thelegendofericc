package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.utils.RectangularBitset;

public class FieldOfViewComponent implements Component {
    public static int VIEW_RADIUS = 6;
    public RectangularBitset visibility;

    public FieldOfViewComponent(int width, int height) {
        this.visibility = new RectangularBitset(width, height);
    }
}

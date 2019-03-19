package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class FieldOfViewComponent implements Component {
    public static final int VIEW_RADIUS = 6;
    public boolean[][] visibility;

    public FieldOfViewComponent(int width, int height) {
        this.visibility = new boolean[width][height];
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.utils.GridPoint;
import com.ericc.the.game.utils.RectangularBitset;

import java.util.ArrayList;
import java.util.List;

public class FieldOfViewComponent implements Component {
    public static final float RADIUS = 5.5f;
    public int[][] visibility;
    public List<GridPoint> points = new ArrayList<>();
    public int version = 0;

    public FieldOfViewComponent(int width, int height) {
        visibility = new int[width][height];
    }
}

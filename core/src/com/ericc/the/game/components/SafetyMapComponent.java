package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class SafetyMapComponent implements Component {
    public int width;
    public int height;
    public int[][] distance;
}

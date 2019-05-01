package com.ericc.the.game.utils;

import java.util.Comparator;

public class WeightedGridPoint {
    public GridPoint xy;
    public int weight;

    public WeightedGridPoint(GridPoint xy, int weight) {
        this.xy = xy;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return xy.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == hashCode();
    }

    public static class WeightedGridPointComparator implements Comparator<WeightedGridPoint> {
        public int compare(WeightedGridPoint gp1, WeightedGridPoint gp2) {
            return Integer.compare(gp1.weight, gp2.weight);
        }
    }
}

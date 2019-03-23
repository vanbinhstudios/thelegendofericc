package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class DescendingComponent implements Component {
    public boolean descending;

    public DescendingComponent(boolean descending) {
        this.descending = descending;
    }
}

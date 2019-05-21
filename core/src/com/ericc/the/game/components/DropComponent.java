package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.drops.Drop;

public class DropComponent implements Component {
    public Drop drop;

    public DropComponent(Drop drop) {
        this.drop = drop;
    }
}

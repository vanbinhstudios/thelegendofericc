package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class LootComponent implements Component {
    public Item item;

    public LootComponent(Item item) {
        this.item = item;
    }
}

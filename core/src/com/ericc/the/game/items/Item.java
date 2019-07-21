package com.ericc.the.game.items;

import com.ericc.the.game.components.Model;
import com.ericc.the.game.effects.Effect;

import java.util.Arrays;
import java.util.List;

public class Item {
    public List<Effect> effects;
    public Model model;
    public ItemType type;
    public String name;
    public Integer quantity;

    public Item(String name, Model model, ItemType type, Effect... effects) {
        this.effects = Arrays.asList(effects);
        this.model = model;
        this.type = type;
        this.name = name;
        this.quantity = 1;
    }
}

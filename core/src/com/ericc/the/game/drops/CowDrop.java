package com.ericc.the.game.drops;

import com.ericc.the.game.Models;
import com.ericc.the.game.items.Item;
import com.ericc.the.game.items.ItemType;
import com.ericc.the.game.effects.Heal;

public class CowDrop implements Drop {
    @Override
    public Item drop() {
        double choice = Math.random();
        if (choice < 0.5) return new Item("Cow meat", Models.food, ItemType.CARRIED, new Heal(50));
        return null;
    }
}

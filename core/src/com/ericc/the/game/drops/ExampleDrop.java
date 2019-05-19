package com.ericc.the.game.drops;

import com.ericc.the.game.Models;
import com.ericc.the.game.components.Item;
import com.ericc.the.game.components.ItemType;
import com.ericc.the.game.effects.AddArrows;
import com.ericc.the.game.effects.GrantInvulnerability;
import com.ericc.the.game.effects.Heal;
import com.ericc.the.game.effects.SpawnStorm;

public class ExampleDrop implements Drop {
    @Override
    public Item drop() {
        double choice = Math.random();
        if (choice < 0.2) return new Item("Small heal", Models.sword, ItemType.INSTANT, new Heal(30));
        if (choice < 0.4) return new Item("Arrow", Models.arrow, ItemType.INSTANT, new AddArrows(1));
        if (choice < 0.6) return new Item("Scroll of storm", Models.explosion3, ItemType.CARRIED, new SpawnStorm());
        if (choice < 0.8)
            return new Item("Scroll of invulnerability", Models.crate, ItemType.CARRIED, new GrantInvulnerability(500));
        return null;
    }
}

package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.InventoryComponent;

public class SwitchItem extends Action {
    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        InventoryComponent inventory = Mappers.inventory.get(entity);
        if (inventory != null && inventory.items.size() > 0) {
            inventory.chosen += 1;
            inventory.chosen %= inventory.items.size();
        }
    }
}

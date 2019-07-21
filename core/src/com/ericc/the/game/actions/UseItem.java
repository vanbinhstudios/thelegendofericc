package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.InventoryComponent;
import com.ericc.the.game.items.Item;
import com.ericc.the.game.effects.Effect;

public class UseItem extends Action {
    @Override
    public int getDelay() {
        return 100;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        return false;
    }

    @Override
    public void execute(Entity entity, Engine engine) {
        InventoryComponent inventory = Mappers.inventory.get(entity);
        if (inventory != null && inventory.items.size() > 0) {
            Item item = inventory.items.get(inventory.chosen);
            item.quantity--;

            if (item.quantity == 0) {
                inventory.items.remove(inventory.chosen);
            }

            if (inventory.items.size() > 0) {
                inventory.chosen %= inventory.items.size();
            }
            for (Effect e : item.effects) {
                e.apply(entity, engine);
            }
        }
    }
}

package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.*;
import com.ericc.the.game.utils.GridPoint;

public class MoveBy implements Effect {
    private GridPoint offset;

    public MoveBy(GridPoint offset) {
        this.offset = offset;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        pos.map.collisionMap.remove(pos.xy);
        pos.xy = pos.xy.add(offset);
        pos.map.collisionMap.put(pos.xy, entity);

        Entity trap = pos.map.trapMap.get(pos.xy);
        if (trap != null) {
            trap.add(ActivatedComponent.ACTIVE);
        }

        Entity loot = pos.map.lootMap.get(pos.xy);
        if (loot != null) {
            Item item = Mappers.loot.get(loot).item;
            if (item != null) {
                if (item.type == ItemType.INSTANT) {
                    for (Effect e : item.effects) {
                        e.apply(entity, engine);
                    }
                } else if (item.type == ItemType.CARRIED) {
                    InventoryComponent inventory = Mappers.inventory.get(entity);
                    if (inventory != null) {
                        boolean isIn = false;

                        for (Item itemInv : inventory.items) {
                            if (item.name.equals(itemInv.name)) {
                                itemInv.quantity++;
                                isIn = true;

                                break;
                            }
                        }

                        if (!isIn) {
                            inventory.items.add(item);
                        }
                    }
                }
                pos.map.lootMap.remove(pos.xy);
                loot.remove(PositionComponent.class);
            }
        }

        entity.add(DirtyFlag.DIRTY);
    }
}

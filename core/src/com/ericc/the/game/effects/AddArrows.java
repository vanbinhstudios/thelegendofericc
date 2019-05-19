package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.StatsComponent;

public class AddArrows implements Effect {
    int amount;

    public AddArrows(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        StatsComponent stats = Mappers.stats.get(entity);
        if (stats != null) {
            stats.arrows += amount;
        }
    }
}

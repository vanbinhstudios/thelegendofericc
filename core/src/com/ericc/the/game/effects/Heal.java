package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.StatsComponent;

public class Heal implements Effect {
    private int value;

    public Heal(int value) {
        this.value = value;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        StatsComponent targetStats = Mappers.stats.get(entity);
        if (targetStats != null) {
            targetStats.health += value;
            targetStats.health = Math.min(targetStats.health, targetStats.maxHealth);
        }
    }
}

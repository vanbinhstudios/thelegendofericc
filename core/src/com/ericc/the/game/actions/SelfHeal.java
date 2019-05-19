package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.StatsComponent;

public class SelfHeal extends Action {
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
        StatsComponent stats = Mappers.stats.get(entity);
        stats.health += 5;
        stats.mana += 10;

        if (stats.health > stats.maxHealth)
            stats.health = stats.maxHealth;

        if (stats.mana > stats.maxMana)
            stats.mana = stats.maxMana;
    }
}

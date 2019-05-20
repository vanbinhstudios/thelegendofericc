package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.StatsComponent;

public class InflictDamage implements Effect {
    private int amount;
    private Entity attacker;

    public InflictDamage(int amount, Entity attacker) {
        this.amount = amount;
        this.attacker = attacker;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        StatsComponent stats = Mappers.stats.get(entity);
        if (stats != null && stats.invulnerabilityTime <= 0) {
            stats.health -= amount;
            if (stats.health <= 0) {
                new Kill(attacker).apply(entity, engine);
            }
        }
    }
}

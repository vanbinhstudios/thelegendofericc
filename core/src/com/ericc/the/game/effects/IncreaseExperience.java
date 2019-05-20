package com.ericc.the.game.effects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.StatsComponent;
import com.ericc.the.game.ui.actors.Popup;

public class IncreaseExperience implements Effect {
    private int amount;

    public IncreaseExperience(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Entity entity, Engine engine) {
        StatsComponent stats = Mappers.stats.get(entity);
        stats.experience += amount;
        // TODO A proper levelling system
        while (stats.experience > stats.maxExperience) {
            stats.level++;
            stats.experience -= stats.maxExperience;
            stats.maxExperience *= 1.5;
            stats.health += Math.min(stats.level * 25, stats.maxHealth - stats.health);
            stats.mana += Math.min(stats.level * 25, stats.maxMana - stats.mana);

            if (Mappers.player.has(entity)) {
                if (stats.level == 2) {
                    Popup.show("You unlocked FIRE BEAM");
                } else if (stats.level == 4) {
                    // TODO change name
                    Popup.show("You unlocked PIERDZENIE");
                } else {
                    Popup.show("No skills gained this time");
                }
            }
        }
    }
}

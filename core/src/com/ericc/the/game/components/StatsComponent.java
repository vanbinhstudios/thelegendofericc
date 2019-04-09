package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class StatsComponent implements Component {
    public int intelligence;
    public int agility;
    public int strength;
    public int maxHealth;
    public int health;

    public StatsComponent(int intelligence, int agility, int strength, int health) {
        this.intelligence = intelligence;
        this.agility = agility;
        this.strength = strength;
        this.maxHealth = health;
        this.health = this.maxHealth;
    }
}

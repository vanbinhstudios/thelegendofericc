package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class StatsComponent implements Component {
    public static int FIRST_LEVEL_EXPERIENCE = 1000;
    public int intelligence;
    public int agility;
    public int strength;
    public int maxHealth;
    public int health;
    public int maxMana;
    public int mana;
    public int experience;
    public int level;
    public int arrows;
    public int maxExperience;
    public float delayMultiplier;
    public boolean canUseSkills;
    public int invulnerabilityTime = 0;
    public int lives;

    public StatsComponent(int intelligence, int agility, int strength, int health, boolean canUseSkills, int lives) {
        this(intelligence, agility, strength, health, 0, canUseSkills);
        this.lives = lives;
    }

    public StatsComponent(int intelligence, int agility, int strength, int health, boolean canUseSkills) {
        this(intelligence, agility, strength, health, 0, canUseSkills);
    }

    public StatsComponent(int intelligence, int agility, int strength, int health, int mana) {
        this(intelligence, agility, strength, health, mana, true);
    }

    public StatsComponent(int intelligence, int agility, int strength, int health, int mana, boolean canUseSkills) {
        this.intelligence = intelligence;
        this.agility = agility;
        this.strength = strength;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.delayMultiplier = 1f;
        this.experience = 0;
        this.arrows = 30;
        this.level = 1;
        this.maxExperience = FIRST_LEVEL_EXPERIENCE;
        this.canUseSkills = canUseSkills;
        this.lives = 1;
    }

    public void tick(int time) {
        invulnerabilityTime -= time;
        invulnerabilityTime = Math.max(0, invulnerabilityTime);
    }
}

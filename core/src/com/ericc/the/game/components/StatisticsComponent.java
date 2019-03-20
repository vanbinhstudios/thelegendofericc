package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class StatisticsComponent implements Component{
    public int health;
    public int movementPoints;
    public int agility;
    public int strength;
    public int intelligence;

    public StatisticsComponent(int health, int movementPoints, int agility, int strength, int intelligence) {
        this.health = health;
        this.movementPoints = movementPoints;
        this.agility = agility;
        this.strength = strength;
        this.intelligence = intelligence;
    }
}

package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class StatisticsComponent implements Component{
    public int health;
    public int movement_points;
    public int agility;
    public int strength;
    public int intelligence;

    public StatisticsComponent(int health, int movement_points, int agility, int strength, int intelligence) {
        this.health = health;
        this.movement_points = movement_points;
        this.agility = agility;
        this.strength = strength;
        this.intelligence = intelligence;
    }
}

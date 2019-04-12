package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
    public int damage;

    public AttackComponent(int value) {
        this.damage = value;
    }
}

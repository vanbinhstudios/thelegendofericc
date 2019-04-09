package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {
    public int damage;

    public DamageComponent(int value) {
        this.damage = value;
    }
}

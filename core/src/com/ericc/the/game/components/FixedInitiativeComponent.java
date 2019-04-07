package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

public class FixedInitiativeComponent implements Component {
    public final int initiative;

    public FixedInitiativeComponent(int initiative) {
        this.initiative = initiative;
    }
}

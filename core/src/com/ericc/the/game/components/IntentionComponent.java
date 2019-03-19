package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.actions.Action;

public class IntentionComponent implements Component {
    public Action currentIntent;

    public IntentionComponent(Action intent) {
        this.currentIntent = intent;
    }
}

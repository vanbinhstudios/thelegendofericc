package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.actions.Action;

public class CurrentActionComponent implements Component {
    public Action action;

    public CurrentActionComponent(Action action) {
        this.action = action;
    }
}

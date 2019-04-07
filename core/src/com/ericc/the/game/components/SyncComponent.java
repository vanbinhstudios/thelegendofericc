package com.ericc.the.game.components;

import com.ericc.the.game.actions.Action;

public class SyncComponent extends Action {
    public static final SyncComponent SYNC = new SyncComponent();

    @Override
    public int getDelay() {
        return 0;
    }
}

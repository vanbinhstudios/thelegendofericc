package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.EntitySystem;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.DescendingComponent;
import com.ericc.the.game.components.IntentionComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.CurrentMap;
import com.ericc.the.game.map.Dungeon;

public class TeleportPlayerSystem extends EntitySystem {

    private Dungeon dungeon;
    private Engines engines;
    private Player player;

    public TeleportPlayerSystem(Dungeon dungeon, Engines engines, Player player) {
        super(10000);

        this.dungeon = dungeon;
        this.engines = engines;
        this.player = player;
    }


    @Override
    public void update(float deltaTime) {
        IntentionComponent intention = Mappers.intention.get(player);

        if (intention.currentIntent instanceof TeleportAction) {
            TeleportAction teleportAction = (TeleportAction) intention.currentIntent;
            DescendingComponent stairs = Mappers.stairsComponent.get(teleportAction.stairs);

            Mappers.intention.get(player).currentIntent = Actions.NOTHING;

            if (stairs.descending) {
                CurrentMap.setMap(dungeon.goToNext(), engines);
            } else {
                CurrentMap.setMap(dungeon.goToPrevious(), engines);
            }
        }
    }
}

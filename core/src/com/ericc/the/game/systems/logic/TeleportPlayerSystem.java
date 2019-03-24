package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.EntitySystem;
import com.ericc.the.game.Engines;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.IntentionComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.StaircaseDestinationComponent;
import com.ericc.the.game.entities.Player;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.map.InitialPlayerPosition;
import com.ericc.the.game.map.StaircaseDestination;

public class TeleportPlayerSystem extends EntitySystem {

    private Dungeon dungeon;
    private Player player;
    private Engines engines;

    public TeleportPlayerSystem(Dungeon dungeon, Engines engines, Player player) {
        super(103);

        this.dungeon = dungeon;
        this.engines = engines;
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        IntentionComponent intention = Mappers.intention.get(player);

        if (intention.currentIntent instanceof TeleportAction) {
            TeleportAction teleportAction = (TeleportAction) intention.currentIntent;
            StaircaseDestinationComponent stairs = Mappers.stairsComponent.get(teleportAction.stairs);
            FieldOfViewComponent fov = Mappers.fov.get(player);
            PositionComponent pos = Mappers.position.get(player);
            final int MARGIN = FieldOfViewComponent.VIEW_RADIUS;

            for (int x = pos.x - MARGIN; x <= pos.x + MARGIN; ++x) {
                for (int y = pos.y - MARGIN; y <= pos.y + MARGIN; ++y) {
                    fov.visibility.clear(x, y);
                }
            }

            Mappers.intention.get(player).currentIntent = Actions.NOTHING;

            if (stairs.destination == StaircaseDestination.DESCENDING) {
                dungeon.changeLevel(dungeon.getCurrentLevelNumber() + 1,
                        InitialPlayerPosition.LEVEL_ENTRANCE);
            } else {
                dungeon.changeLevel(dungeon.getCurrentLevelNumber() - 1,
                        InitialPlayerPosition.LEVEL_EXIT);
            }

            engines.updatePlayersVision();
        }
    }
}

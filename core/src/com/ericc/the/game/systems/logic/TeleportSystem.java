package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.TeleportAction;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.map.InitialPlayerPosition;
import com.ericc.the.game.map.StaircaseDestination;

public class TeleportSystem extends IteratingSystem {
    private Dungeon dungeon;

    public TeleportSystem(Dungeon dungeon, int priority) {
        super(Family.all(PositionComponent.class, TeleportAction.class).get(), priority);

        this.dungeon = dungeon;
    }

    @Override
    protected void processEntity(Entity teleporter, float deltaTime) {
        PositionComponent pos = Mappers.position.get(teleporter);
        TeleportAction tp = Mappers.teleport.get(teleporter);
        if (pos.map.hasAnimationDependency(pos.xy)) {
            teleporter.add(SyncComponent.SYNC);
            return;
        }


        Entity subject = pos.map.entityMap.get(pos.xy);
        if (subject == null || !Mappers.player.has(subject)) {
            teleporter.remove(TeleportAction.class);
            return;
        }

        FieldOfViewComponent subjectFov = Mappers.fov.get(subject);

        if (subjectFov != null) {
            final int MARGIN = FieldOfViewComponent.VIEW_RADIUS;
            for (int x = pos.getX() - MARGIN; x <= pos.getX() + MARGIN; ++x) {
                for (int y = pos.getY() - MARGIN; y <= pos.getY() + MARGIN; ++y) {
                    subjectFov.visibility.clear(x, y);
                }
            }
        }

        if (tp.dest == StaircaseDestination.DESCENDING) {
            dungeon.changeLevel(dungeon.getCurrentLevelNumber() + 1,
                    InitialPlayerPosition.LEVEL_ENTRANCE);
        } else {
            dungeon.changeLevel(dungeon.getCurrentLevelNumber() - 1,
                    InitialPlayerPosition.LEVEL_EXIT);
        }

        teleporter.remove(TeleportAction.class);
    }
}

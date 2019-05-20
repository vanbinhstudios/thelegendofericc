package com.ericc.the.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.components.DirtyFlag;
import com.ericc.the.game.components.FieldOfViewComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.map.Dungeon;
import com.ericc.the.game.map.InitialPlayerPosition;
import com.ericc.the.game.map.StaircaseDestination;

public class TeleportAction extends Action {
    public StaircaseDestination dest;
    private Dungeon dungeon;

    public TeleportAction(StaircaseDestination dest, Dungeon dungeon) {
        this.dest = dest;
        this.dungeon = dungeon;
    }

    @Override
    public int getDelay() {
        return 1;
    }

    @Override
    public boolean needsSync(Entity entity, Engine engine) {
        PositionComponent pos = Mappers.position.get(entity);
        return pos.map.hasAnimationDependency(pos.xy);
    }

    @Override
    public void execute(Entity teleporter, Engine engine) {
        PositionComponent pos = Mappers.position.get(teleporter);

        Entity subject = pos.map.collisionMap.get(pos.xy);
        if (subject == null || !Mappers.player.has(subject)) {
            return;
        }

        subject.add(DirtyFlag.DIRTY);

        FieldOfViewComponent subjectFov = Mappers.fov.get(subject);

        if (subjectFov != null) {
            final int MARGIN = FieldOfViewComponent.VIEW_RADIUS;
            for (int x = pos.getX() - MARGIN; x <= pos.getX() + MARGIN; ++x) {
                for (int y = pos.getY() - MARGIN; y <= pos.getY() + MARGIN; ++y) {
                    subjectFov.visibility.clear(x, y);
                }
            }
        }

        if (dest == StaircaseDestination.DESCENDING) {
            dungeon.changeLevel(dungeon.getCurrentLevelNumber() + 1,
                    InitialPlayerPosition.LEVEL_ENTRANCE);
        } else {
            dungeon.changeLevel(dungeon.getCurrentLevelNumber() - 1,
                    InitialPlayerPosition.LEVEL_EXIT);
        }
    }
}

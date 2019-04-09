package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.utils.GridPoint;

public class MovementSystem extends IteratingSystem {
    public MovementSystem(int priority) {
        super(Family.all(PositionComponent.class, MovementAction.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = Mappers.position.get(entity);
        MovementAction move = Mappers.movementAction.get(entity);

        if (pos.map.hasAnimationDependency(pos.xy)) {
            entity.add(SyncComponent.SYNC);
            return;
        }

        GridPoint offset = null;

        pos.direction = move.direction;

        if (pos.map.isPassable(pos.xy.add(GridPoint.fromDirection(pos.direction)))) {
            offset = GridPoint.fromDirection(pos.direction);
        }

        if (offset != null) {
            pos.map.entityMap.remove(pos.xy);
            // TODO Decouple animation speed from movement cost
            entity.add(new AnimationComponent(new JumpAnimation(new Vector2(offset.x, offset.y),
                    0.6f * move.delay / 100, 0.15f * move.delay / 100)));
            pos.xy = pos.xy.add(offset);
            pos.map.entityMap.put(pos.xy, entity);
        }

        entity.remove(MovementAction.class);
    }
}

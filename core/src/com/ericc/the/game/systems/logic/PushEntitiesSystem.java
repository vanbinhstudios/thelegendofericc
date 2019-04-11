package com.ericc.the.game.systems.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.ericc.the.game.Mappers;
import com.ericc.the.game.actions.Action;
import com.ericc.the.game.actions.Actions;
import com.ericc.the.game.actions.MovementAction;
import com.ericc.the.game.actions.PushAction;
import com.ericc.the.game.animations.JumpAnimation;
import com.ericc.the.game.components.AnimationComponent;
import com.ericc.the.game.components.PositionComponent;
import com.ericc.the.game.components.SyncComponent;
import com.ericc.the.game.entities.PushableObject;
import com.ericc.the.game.utils.GridPoint;

import java.util.ArrayList;
import java.util.Stack;

public class PushEntitiesSystem extends IteratingSystem {

    private Stack<Entity> queue;

    public PushEntitiesSystem(int priority) {
        super(Family.all(PositionComponent.class, PushAction.class).get(), priority);
        this.queue = new Stack<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        queue.clear();

        PositionComponent pos = Mappers.position.get(entity);
        PushAction push = Mappers.pushAction.get(entity);

        if (!canPush(pos, push)) {
            entity.remove(PushAction.class);
            return;
        }

        if (hasAnimationDependency(pos, push)) {
            entity.add(SyncComponent.SYNC);
            return;
        }

        movePushableEntities(pos, push);

        while (!queue.empty()) {
            queue.pop().add(Actions.PUSH(push.direction, push.delay));
        }

        entity.add(Actions.PUSH(push.direction, push.delay));
        entity.remove(PushAction.class);
    }

    private boolean canPush(PositionComponent pos, PushAction push) {
        GridPoint fromPush = GridPoint.fromDirection(push.direction);
        GridPoint start = pos.xy.add(fromPush);

        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (Mappers.hostile.has(pos.map.getEntity(start))) {
                return false;
            }

            start = start.add(fromPush);
        }

        return pos.map.isPassable(start);
    }

    private boolean hasAnimationDependency(PositionComponent pos, PushAction push) {
        GridPoint fromPush = GridPoint.fromDirection(push.direction);
        GridPoint start = pos.xy.add(fromPush);

        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (pos.map.hasAnimationDependency(start)) {
                return true;
            }

            start = start.add(fromPush);
        }

        return pos.map.hasAnimationDependency(start);
    }

    private void movePushableEntities(PositionComponent pos, PushAction push) {
        GridPoint fromPush = GridPoint.fromDirection(push.direction);
        GridPoint start = pos.xy.add(fromPush);

        while (pos.map.isFloor(start) && !pos.map.isPassable(start)) {
            if (Mappers.hostile.has(pos.map.getEntity(start))) {
                return;
            }

            queue.add(pos.map.getEntity(start));
            start = start.add(fromPush);
        }
    }
}
